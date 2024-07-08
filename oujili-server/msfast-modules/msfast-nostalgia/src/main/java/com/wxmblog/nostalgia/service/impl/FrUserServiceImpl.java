package com.wxmblog.nostalgia.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wxmblog.base.auth.authority.service.WxAppletService;
import com.wxmblog.base.auth.common.rest.response.WxAppletOpenResponse;
import com.wxmblog.base.auth.service.MsfConfigService;
import com.wxmblog.base.common.entity.LoginUser;
import com.wxmblog.base.common.enums.BaseUserExceptionEnum;
import com.wxmblog.base.common.exception.JrsfException;
import com.wxmblog.base.common.service.BaseCommonService;
import com.wxmblog.base.common.utils.*;
import com.wxmblog.base.file.service.MsfFileService;
import com.wxmblog.base.websocket.common.constant.WebSocketConstants;
import com.wxmblog.nostalgia.common.constant.Constants;
import com.wxmblog.nostalgia.common.enums.user.*;
import com.wxmblog.nostalgia.common.exception.UserExceptionEnum;
import com.wxmblog.nostalgia.common.rest.request.admin.statistic.UserRegisterStatisticRequest;
import com.wxmblog.nostalgia.common.rest.request.admin.user.UserAdminInfoAddRequest;
import com.wxmblog.nostalgia.common.rest.request.admin.user.UserExamineRequest;
import com.wxmblog.nostalgia.common.rest.request.admin.user.UserInfoRequest;
import com.wxmblog.nostalgia.common.rest.request.admin.user.UserPageRequest;
import com.wxmblog.nostalgia.common.rest.request.fruser.*;
import com.wxmblog.nostalgia.common.rest.request.payment.SeeLikeMeRequest;
import com.wxmblog.nostalgia.common.rest.response.admin.statistic.OutlineResponse;
import com.wxmblog.nostalgia.common.rest.response.admin.statistic.ProportionResponse;
import com.wxmblog.nostalgia.common.rest.response.admin.user.*;
import com.wxmblog.nostalgia.common.rest.response.front.article.UserArticleViewResponse;
import com.wxmblog.nostalgia.common.rest.response.front.fruser.*;
import com.wxmblog.nostalgia.common.rest.response.front.payment.PayMenuResponse;
import com.wxmblog.nostalgia.common.rest.response.front.payment.PayMoneyResponse;
import com.wxmblog.nostalgia.common.rest.response.front.payment.ViewLikeMeData;
import com.wxmblog.nostalgia.common.rest.response.front.payment.ViewLikeMeResponse;
import com.wxmblog.nostalgia.dao.ArticleDao;
import com.wxmblog.nostalgia.dao.FrUserDao;
import com.wxmblog.nostalgia.entity.*;
import com.wxmblog.nostalgia.service.*;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service("frUserService")
public class FrUserServiceImpl extends ServiceImpl<FrUserDao, FrUserEntity> implements FrUserService {

    @Autowired
    MsfConfigService msfConfigService;

    @Autowired
    UserMatchingService userMatchingService;

    @Autowired
    RecommendConfigService recommendConfigService;

    @Autowired
    FrUserExamineService frUserExamineService;

    @Autowired
    MsfFileService msfFileService;

    @Autowired
    RedissonClient redissonClient;


    @Autowired
    WxAppletService wxAppletService;

    @Autowired
    BaseCommonService baseCommonService;

    @Autowired
    ArticleDao articleDao;

    @Override
    public Long countByOpenId(String openId) {
        Wrapper<FrUserEntity> frUserEntityWrapper = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getOpenId, openId);
        return count(frUserEntityWrapper);
    }

    @Override
    public FrUserEntity getFrUserByOpenId(String openId) {
        Wrapper<FrUserEntity> frUserEntityWrapper = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getOpenId, openId);
        return getOne(frUserEntityWrapper);
    }

    @Override
    public RecommendUserInfoResponse getRecommendUserInfo(RecommendUserRequest request) {

        LoginUser<LoginResponse> loginUser = TokenUtils.info(LoginResponse.class);
        Integer num = Integer.valueOf(msfConfigService.getValueByCode(SysConfigCodeEnum.recommendTotal.name()));
        if (loginUser == null) {
            //未登陆
            if (request == null || request.getAge() == null || request.getGender() == null) {
                throw new JrsfException(UserExceptionEnum.SEARCH_PARAM_EMPTY_EXCEPTION);
            }
            Map<String, Object> param = new HashMap<>();
            param.put("gender", request.getGender().name());

            Calendar calendarStart = Calendar.getInstance();
            calendarStart.add(Calendar.YEAR, -(request.getAge() + Constants.AGE_DIFFER));
            param.put("startDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.beginOfYear(calendarStart.getTime())));

            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.add(Calendar.YEAR, -(request.getAge() - Constants.AGE_DIFFER));
            param.put("endDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.endOfYear(calendarEnd.getTime())));
            RecommendUserInfoResponse userInfoResponse = getRecommendUserInfoByParam(param);
            if (userInfoResponse != null) {
                userInfoResponse.setSurplusNum(num);
            } else {
                Map<String, Object> paramNUll = new HashMap<>();
                paramNUll.put("gender", request.getGender().name());
                userInfoResponse = getRecommendUserInfoByParam(paramNUll);
                if (userInfoResponse != null) {
                    userInfoResponse.setSurplusNum(num);
                }
            }

            if (userInfoResponse != null) {
                userInfoResponse.setUserArticleViewResponse(getUserArticleView(userInfoResponse.getId()));
            }
            return userInfoResponse;
        } else {

            RLock lock = redissonClient.getLock(Constants.MATCHING + TokenUtils.getOwnerId());
            try {
                lock.lock();
                //已登录
                FrUserEntity frUserEntity = this.getById(loginUser.getId());
                if (frUserEntity == null) {
                    throw new JrsfException(BaseUserExceptionEnum.USER_STATUS_ERROR_EXCEPTION);
                }
                Map<String, Object> param = new HashMap<>();
                param.put("gender", frUserEntity.getGender().name());
                param.put("selfId", frUserEntity.getId());
                //默认配置信息
                Calendar calendarStart = Calendar.getInstance();
                calendarStart.setTime(frUserEntity.getBirthday());
                calendarStart.add(Calendar.YEAR, -Constants.AGE_DIFFER);
                param.put("startDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.beginOfYear(calendarStart.getTime())));
                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTime(frUserEntity.getBirthday());
                calendarEnd.add(Calendar.YEAR, Constants.AGE_DIFFER);
                param.put("endDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.endOfYear(calendarEnd.getTime())));

                if (StringUtils.isNotBlank(frUserEntity.getCity())) {
                    List<String> city = new ArrayList<>();
                    city.add(frUserEntity.getCity());
                    param.put("city", city);
                }

                //根据配置信息搜索
                RecommendConfigEntity recommendConfigEntity = this.recommendConfigService.getRecommendConfigByUserId(TokenUtils.getOwnerId());
                if (recommendConfigEntity != null) {
                    if (CollectionUtil.isNotEmpty(recommendConfigEntity.getCity())) {
                        param.put("city", recommendConfigEntity.getCity());
                    }

                    if (recommendConfigEntity.getMinAge() != null) {
                        Calendar calendarEndConfig = Calendar.getInstance();
                        calendarEndConfig.add(Calendar.YEAR, -recommendConfigEntity.getMinAge());
                        param.put("endDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.endOfYear(calendarEndConfig.getTime())));
                    }

                    if (recommendConfigEntity.getMaxAge() != null) {
                        Calendar calendarStartConfig = Calendar.getInstance();
                        calendarStartConfig.add(Calendar.YEAR, -recommendConfigEntity.getMaxAge());
                        param.put("startDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.beginOfYear(calendarStartConfig.getTime())));
                    }
                }
                RecommendUserInfoResponse userInfoResponse = getRecommendUserInfoByParam(param);
                Integer numSize = num - Integer.valueOf(userMatchingService.matchingNum().toString());
                if (numSize == 0) {
                    return null;
                }
                if (userInfoResponse != null) {
                    userInfoResponse.setSurplusNum(numSize);
                } else {
                    Map<String, Object> paramNUll = new HashMap<>();
                    paramNUll.put("gender", frUserEntity.getGender().name());
                    paramNUll.put("selfId", frUserEntity.getId());
                    paramNUll.put("size", 1);
                    userInfoResponse = getRecommendUserInfoByParam(paramNUll);
                    if (userInfoResponse != null) {
                        userInfoResponse.setSurplusNum(numSize);
                    }
                }
                if (userInfoResponse != null) {
                    userInfoResponse.setUserArticleViewResponse(getUserArticleView(userInfoResponse.getId()));
                }
                return userInfoResponse;
            } finally {
                lock.unlock();
            }

        }
    }

    private UserArticleViewResponse getUserArticleView(Integer id) {

        UserArticleViewResponse response = new UserArticleViewResponse();
        response.setArticleImg(articleDao.getUserArticleView(id));
        if (CollectionUtil.isEmpty(response.getArticleImg())) {
            Wrapper<ArticleEntity> query = new QueryWrapper<ArticleEntity>().lambda().eq(ArticleEntity::getUserId, id).isNotNull(ArticleEntity::getContent).orderByDesc(ArticleEntity::getId).last("limit 1");
            ArticleEntity articleEntity = articleDao.selectOne(query);
            if (articleEntity != null) {
                response.setArticleContent(articleEntity.getContent());
            }
        }
        return response;
    }

    @Override
    @Transactional
    @Async
    public void saveRecommendConfig(FrUserEntity frUserEntity) {
        RecommendConfigEntity recommendConfigEntity = new RecommendConfigEntity();
        recommendConfigEntity.setUserId(frUserEntity.getId());
        if (StringUtils.isNotBlank(frUserEntity.getCity())) {
            List<String> city = new ArrayList<>();
            city.add(frUserEntity.getCity());
            recommendConfigEntity.setCity(city);
        }

        if (frUserEntity.getBirthday() != null) {
            Integer age = DateUtils.getAgeByBirth(frUserEntity.getBirthday());
            recommendConfigEntity.setMinAge(age - Constants.AGE_DIFFER);
            recommendConfigEntity.setMaxAge(age + Constants.AGE_DIFFER);
        }
        recommendConfigService.save(recommendConfigEntity);
    }

    @Override
    public RecommendConfigResponse getRecommendConfig() {
        RecommendConfigResponse response = new RecommendConfigResponse();

        FrUserEntity frUserEntity = getById(TokenUtils.getOwnerId());
        if (frUserEntity != null) {

            if (StringUtils.isNotBlank(frUserEntity.getCity())) {
                List<String> city = new ArrayList<>();
                city.add(frUserEntity.getCity());
                response.setCity(city);
            }
            if (frUserEntity.getBirthday() != null) {
                Integer age = DateUtils.getAgeByBirth(frUserEntity.getBirthday());
                response.setMaxAge(age + Constants.AGE_DIFFER);
                response.setMinAge(age - Constants.AGE_DIFFER);
            }
        }

        RecommendConfigEntity recommendConfigEntity = recommendConfigService.getRecommendConfigByUserId(TokenUtils.getOwnerId());
        if (recommendConfigEntity != null) {
            BeanUtils.copyProperties(recommendConfigEntity, response);
        }
        return response;
    }

    @Override
    @Transactional
    public void updateConfigInfo(RecommendConfigRequest request) {

        if (request.getMinAge().compareTo(request.getMaxAge()) > 0) {
            throw new JrsfException(UserExceptionEnum.MIN_AGE_GREATER_EXCEPTION);
        }
        RecommendConfigEntity recommendConfigEntity = recommendConfigService.getRecommendConfigByUserId(TokenUtils.getOwnerId());
        if (recommendConfigEntity == null) {
            recommendConfigEntity = new RecommendConfigEntity();
            recommendConfigEntity.setUserId(TokenUtils.getOwnerId());
        }

        BeanUtils.copyProperties(request, recommendConfigEntity);
        recommendConfigService.saveOrUpdate(recommendConfigEntity);
    }

    @Override
    public void updateLatelyTime(Integer userId) {

        this.update(null, new LambdaUpdateWrapper<FrUserEntity>().set(FrUserEntity::getLatelyTime, new Date()).eq(FrUserEntity::getId, userId));
    }

    @Override
    public PersonalCenterResponse getPersonalCenter() {

        PersonalCenterResponse response = new PersonalCenterResponse();
        FrUserEntity frUserEntity = this.getById(TokenUtils.getOwnerId());
        if (frUserEntity != null) {
            BeanUtils.copyProperties(frUserEntity, response);
            if (frUserEntity.getAdditional() != null) {
                BeanUtils.copyProperties(frUserEntity.getAdditional(), response);
            }

            response.setCompletionRatio(getRatio(frUserEntity));
            LikeResponse likeResponse = this.baseMapper.getPersonalLike(frUserEntity.getId());
            if (likeResponse != null) {
                BeanUtils.copyProperties(likeResponse, response);
                if (likeResponse.getLikeMe() > 0) {
                    LambdaQueryWrapper<UserMatchingEntity> queryWrapper = new QueryWrapper<UserMatchingEntity>().lambda().eq(UserMatchingEntity::getOtherUser, frUserEntity.getId()).eq(UserMatchingEntity::getResult, true).orderByDesc(UserMatchingEntity::getId).last("limit 1");
                    UserMatchingEntity userMatchingEntity = this.userMatchingService.getBaseMapper().selectOne(queryWrapper);
                    if (userMatchingEntity != null) {
                        FrUserEntity likeMe = this.getById(userMatchingEntity.getUserId());
                        if (likeMe != null) {
                            response.setLikeMeheadPortrait(likeMe.getHeadPortrait());
                        }
                    }
                }
            }

        }

        response.setUserArticleViewResponse(getUserArticleView(response.getId()));
        return response;
    }

    @Override
    public PersonalInfoResponse personalInfo() {


        PersonalInfoResponse personalInfo = new PersonalInfoResponse();
        Integer ownerId = TokenUtils.getOwnerId();
        FrUserEntity frUserEntity = this.getById(ownerId);
        if (frUserEntity != null) {
            BeanUtils.copyProperties(frUserEntity, personalInfo);
            if (frUserEntity.getAdditional() != null) {
                BeanUtils.copyProperties(frUserEntity.getAdditional(), personalInfo);
            }
            if (frUserEntity.getBirthday() != null) {
                personalInfo.setAge(DateUtils.getAgeByBirth(frUserEntity.getBirthday()));
                personalInfo.setConstellation(DateUtils.getConstellation(frUserEntity.getBirthday()));
            }

            if (frUserEntity.getAdditional() != null) {
                if (AuthStatusEnum.EXAMINE.equals(frUserEntity.getAdditional().getWaitApprovedStatus())) {
                    personalInfo.setRemarks(AuthStatusEnum.EXAMINE.getDesc() + ",上传个人的真实照片,可提高通过率哦~");
                } else if (AuthStatusEnum.REFUSE.equals(frUserEntity.getAdditional().getWaitApprovedStatus())) {
                    LambdaQueryWrapper<FrUserExamineEntity> queryWrapper = new QueryWrapper<FrUserExamineEntity>().lambda().eq(FrUserExamineEntity::getUserId, ownerId).eq(FrUserExamineEntity::getAuthType, AuthTypeEnum.InfoAuth).eq(FrUserExamineEntity::getAuthStatus, AuthStatusEnum.REFUSE).orderByDesc(FrUserExamineEntity::getId).last("limit 1");
                    FrUserExamineEntity userExamineEntity = frUserExamineService.getOne(queryWrapper);
                    if (userExamineEntity != null) {
                        personalInfo.setRemarks(userExamineEntity.getRemarks());
                    }
                }
            }


        }

        return personalInfo;
    }

    @Override
    @Transactional
    public void photoEdit(PhotoEditRequest request) {
        RLock lock = redissonClient.getLock(Constants.PHOTO_EDIT + TokenUtils.getOwnerId());
        try {
            lock.lock();
            FrUserEntity frUserEntity = this.getById(TokenUtils.getOwnerId());
            if (frUserEntity == null) {
                throw new JrsfException(BaseUserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
            }

            if (PhotoEditTypeEnum.DELETE.equals(request.getPhotoEditType())) {

                if (StringUtils.isBlank(request.getOldUrl())) {
                    throw new JrsfException(UserExceptionEnum.OLD_URL_NOT_EMPTY_EXCEPTION);
                }

                if (CollectionUtil.isNotEmpty(frUserEntity.getWaitApprovedImg())) {

                    if (frUserEntity.getWaitApprovedImg().get(0).equals(request.getOldUrl())) {
                        throw new JrsfException(UserExceptionEnum.FIRST_PHOTO_NOT_DELETE_EXCEPTION);
                    }

                    frUserEntity.getWaitApprovedImg().removeIf(p -> p.equals(request.getOldUrl()));
                    if (CollectionUtil.isEmpty(frUserEntity.getImgList()) || !frUserEntity.getImgList().contains(request.getOldUrl())) {
                        msfFileService.deleteFileByUrl(request.getOldUrl());
                    }

                }
            } else if (PhotoEditTypeEnum.REPLACE.equals(request.getPhotoEditType())) {
                if (StringUtils.isBlank(request.getNewUrl())) {
                    throw new JrsfException(UserExceptionEnum.NEW_URL_NOT_EMPTY_EXCEPTION);
                }

                if (StringUtils.isBlank(request.getOldUrl())) {
                    throw new JrsfException(UserExceptionEnum.OLD_URL_NOT_EMPTY_EXCEPTION);
                }

                if (CollectionUtil.isNotEmpty(frUserEntity.getWaitApprovedImg())) {
                    Collections.replaceAll(frUserEntity.getWaitApprovedImg(), request.getOldUrl(), request.getNewUrl());
                    if (CollectionUtil.isEmpty(frUserEntity.getImgList()) || !frUserEntity.getImgList().contains(request.getOldUrl())) {
                        msfFileService.deleteFileByUrl(request.getOldUrl());
                    }

                    setUserAuth(frUserEntity, AuthStatusEnum.EXAMINE);
                }
            } else if (PhotoEditTypeEnum.ADD.equals(request.getPhotoEditType())) {

                if (StringUtils.isBlank(request.getNewUrl())) {
                    throw new JrsfException(UserExceptionEnum.NEW_URL_NOT_EMPTY_EXCEPTION);
                }

                if (CollectionUtil.isNotEmpty(frUserEntity.getWaitApprovedImg())) {
                    frUserEntity.getWaitApprovedImg().add(request.getNewUrl());
                } else {
                    List<String> urlList = new ArrayList<>();
                    urlList.add(request.getNewUrl());
                    frUserEntity.setWaitApprovedImg(urlList);
                }
                setUserAuth(frUserEntity, AuthStatusEnum.EXAMINE);
            }
            if (CollectionUtil.isNotEmpty(frUserEntity.getWaitApprovedImg())) {
                frUserEntity.setHeadPortrait(frUserEntity.getWaitApprovedImg().get(0));
                Map<String, Object> map = new HashMap<>();
                map.put(WebSocketConstants.HEAD_PORTRAIT, frUserEntity.getHeadPortrait());
                map.put(WebSocketConstants.NICK_NAME, frUserEntity.getNickName());
                baseCommonService.updateUser(frUserEntity.getId(), map);
            }
            this.updateById(frUserEntity);

            //todo: 演示 用户编辑相册后自动通过 需删除 start
            UserExamineRequest request1 = new UserExamineRequest();
            request1.setUserId(frUserEntity.getId());
            request1.setResult(AuthStatusEnum.PASS);
            request1.setVersion(frUserEntity.getVersion());
            examine(request1);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 1);
            Long secondsComplete = DateUtil.between(new Date(), calendar.getTime(), DateUnit.SECOND);

            //一天后自动将用户信息设置为审核中
            ThreadUtil.getInstance().scheduledThreadPool.schedule(() -> {
                FrUserEntity frUser = getById(frUserEntity.getId());
                if (frUser.getAdditional() == null) {
                    AdditionalResponse additional = new AdditionalResponse();
                    additional.setWaitApprovedStatus(AuthStatusEnum.EXAMINE);
                    frUser.setAdditional(additional);
                } else {
                    frUser.getAdditional().setWaitApprovedStatus(AuthStatusEnum.EXAMINE);
                }
                frUser.setAuthStatus(AuthStatusEnum.EXAMINE);
                this.updateById(frUser);
            }, secondsComplete, TimeUnit.SECONDS);
            //todo: 演示 用户编辑相册后自动通过 需删除 end
        } finally {
            lock.unlock();
        }
    }

    private void setUserAuth(FrUserEntity frUserEntity, AuthStatusEnum examine) {

        if (frUserEntity.getAdditional() == null) {
            AdditionalResponse additional = new AdditionalResponse();
            additional.setWaitApprovedStatus(examine);
            frUserEntity.setAdditional(additional);
        } else {
            frUserEntity.getAdditional().setWaitApprovedStatus(examine);
        }
    }

    @Override
    public BaseInfoResponse baseInfo() {
        BaseInfoResponse response = new BaseInfoResponse();
        FrUserEntity frUserEntity = this.getById(TokenUtils.getOwnerId());
        if (frUserEntity != null) {
            BeanUtils.copyProperties(frUserEntity, response);
            response.setAge(DateUtils.getAgeByBirth(frUserEntity.getBirthday()));
        }
        return response;
    }

    @Override
    public void baseInfoEdit(BaseInfoEditRequest request) {

        RLock lock = redissonClient.getLock(Constants.PHOTO_EDIT + TokenUtils.getOwnerId());
        try {
            lock.lock();
            FrUserEntity frUserEntity = this.getById(TokenUtils.getOwnerId());
            if (frUserEntity != null) {
                BeanUtils.copyProperties(request, frUserEntity);
                frUserEntity.setAuthStatus(AuthStatusEnum.EXAMINE);
                this.updateById(frUserEntity);
                Map<String, Object> map = new HashMap<>();
                map.put(WebSocketConstants.HEAD_PORTRAIT, frUserEntity.getHeadPortrait());
                map.put(WebSocketConstants.NICK_NAME, frUserEntity.getNickName());
                baseCommonService.updateUser(frUserEntity.getId(), map);
            }

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void characterEdit(CharacterRequest request) {
        FrUserEntity frUserEntity = this.getById(TokenUtils.getOwnerId());
        if (frUserEntity != null) {
            CharacterTypeResponse characterType = new CharacterTypeResponse();

            characterType.setExtroversionScore(getCharacter(request.getExtroversionScore()));
            characterType.setImagineScore(getCharacter(request.getImagineScore()));
            characterType.setPerceptualScore(getCharacter(request.getPerceptualScore()));
            characterType.setPlanScore(getCharacter(request.getPlanScore()));

            characterType.setName(getCharacterName(characterType));

            frUserEntity.setCharacterType(characterType);
            this.updateById(frUserEntity);
        }
    }

    @Override
    public DoubleAuthResponse doubleAuth() {
        DoubleAuthResponse response = new DoubleAuthResponse();
        FrUserEntity frUserEntity = this.getById(TokenUtils.getOwnerId());
        if (frUserEntity != null && frUserEntity.getAdditional() != null) {
            response.setIdentityAuth(frUserEntity.getAdditional().getIdentityAuth());
            response.setEducationAuth(frUserEntity.getAdditional().getEducationAuth());
        }
        return response;
    }

    @Override
    public UserInfoResponse userInfo(Integer id) {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        FrUserEntity frUserEntity = this.getById(id);
        if (frUserEntity == null) {
            throw new JrsfException(BaseUserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
        }

        BeanUtils.copyProperties(frUserEntity, userInfoResponse);
        if (frUserEntity.getAdditional() != null) {
            BeanUtils.copyProperties(frUserEntity.getAdditional(), userInfoResponse);
        }
        if (frUserEntity.getBirthday() != null) {
            userInfoResponse.setConstellation(DateUtils.getConstellation(frUserEntity.getBirthday()));
            userInfoResponse.setAge(DateUtils.getAgeByBirth(frUserEntity.getBirthday()));
        }
        Integer ownerId = TokenUtils.getOwnerId();
        Wrapper<UserMatchingEntity> queryWrapper = new QueryWrapper<UserMatchingEntity>().lambda().eq(UserMatchingEntity::getUserId, ownerId).eq(UserMatchingEntity::getOtherUser, id);

        UserMatchingEntity userMatchingEntity = userMatchingService.getBaseMapper().selectOne(queryWrapper);
        if (userMatchingEntity != null) {
            if (userMatchingEntity.getResult()) {
                Wrapper<UserMatchingEntity> queryWrapperOther = new QueryWrapper<UserMatchingEntity>().lambda().eq(UserMatchingEntity::getUserId, id).eq(UserMatchingEntity::getOtherUser, ownerId);
                UserMatchingEntity userMatchingEntityOther = userMatchingService.getBaseMapper().selectOne(queryWrapperOther);
                if (userMatchingEntityOther == null) {
                    userInfoResponse.setMatchingStatus(MatchingStatusEnum.LIKE);
                } else {
                    if (userMatchingEntityOther.getResult()) {
                        userInfoResponse.setMatchingStatus(MatchingStatusEnum.SUCCESS);
                    } else {
                        userInfoResponse.setMatchingStatus(MatchingStatusEnum.NOT_LIKE_ME);

                    }
                }

            } else {

                userInfoResponse.setMatchingStatus(MatchingStatusEnum.NOT_LIKE);
            }


        } else {
            userInfoResponse.setMatchingStatus(MatchingStatusEnum.NULL);
        }

        userInfoResponse.setUserArticleViewResponse(getUserArticleView(userInfoResponse.getId()));
        return userInfoResponse;
    }

    @Override
    @Transactional
    public void examine(UserExamineRequest request) {

        RLock lock = redissonClient.getLock(Constants.PHOTO_EDIT + request.getUserId());
        try {
            lock.lock();
            FrUserEntity frUserEntity = this.getById(request.getUserId());
            if (frUserEntity == null) {
                throw new JrsfException(BaseUserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
            }

            if (!frUserEntity.getVersion().equals(request.getVersion())) {
                throw new JrsfException(UserExceptionEnum.USER_VERSION_DIFFERENT_EXCEPTION);
            }

            if (AuthStatusEnum.PASS.equals(request.getResult())) {
                frUserEntity.setAuthStatus(request.getResult());
                //复制图片
                List<String> imgList = frUserEntity.getImgList();
                frUserEntity.setImgList(frUserEntity.getWaitApprovedImg());
                msfFileService.deleteImg(imgList, frUserEntity.getImgList());
            }

            if (frUserEntity.getAdditional() == null) {
                AdditionalResponse additional = new AdditionalResponse();
                additional.setWaitApprovedStatus(request.getResult());
                frUserEntity.setAdditional(additional);
            } else {
                frUserEntity.getAdditional().setWaitApprovedStatus(request.getResult());
            }

            this.updateById(frUserEntity);

            //添加审核记录
            addExamine(request, AuthTypeEnum.InfoAuth);
        } finally {
            lock.unlock();
        }
    }

    private void addExamine(UserExamineRequest request, AuthTypeEnum infoAuth) {

        FrUserExamineEntity frUserExamineEntity = new FrUserExamineEntity();
        frUserExamineEntity.setAuthStatus(request.getResult());
        frUserExamineEntity.setUserId(request.getUserId());
        frUserExamineEntity.setRemarks((!AuthStatusEnum.PASS.equals(request.getResult())) ? request.getResult().getDesc() + " " : "" + request.getRemarks());
        frUserExamineEntity.setAuthType(infoAuth);
        this.frUserExamineService.save(frUserExamineEntity);
    }

    @Override
    public PageResult<UserPageResponse> examinePage(UserPageRequest request, Integer pageIndex, Integer pageSize) {

        Page<UserPageResponse> page = PageHelper.startPage(pageIndex, pageSize);
        this.baseMapper.getExaminePage(request);
        PageResult<UserPageResponse> result = new PageResult<>(page);
        return result;
    }

    @Override
    public UserExamineInfoResponse getExamineInfo(Integer id) {

        UserExamineInfoResponse response = new UserExamineInfoResponse();
        FrUserEntity frUserEntity = this.baseMapper.selectById(id);
        if (frUserEntity != null) {
            BeanUtils.copyProperties(frUserEntity, response);
            if (frUserEntity.getAdditional() != null) {
                response.setWaitApprovedStatus(frUserEntity.getAdditional().getWaitApprovedStatus());
                if (AuthStatusEnum.REFUSE.equals(frUserEntity.getAdditional().getWaitApprovedStatus())) {
                    FrUserExamineEntity frUserExamineEntity = this.frUserExamineService.getExamine(id, AuthTypeEnum.InfoAuth, AuthStatusEnum.REFUSE);
                    if (frUserExamineEntity != null) {
                        response.setRemarks(frUserExamineEntity.getRemarks());
                    }

                }
            }

            response.setConstellation(DateUtils.getConstellation(frUserEntity.getBirthday()));

        }

        return response;
    }

    @Override
    public PageResult<UserIdentityPageResponse> identityPage(UserPageRequest request, Integer pageIndex, Integer pageSize) {
        Page<UserIdentityPageResponse> page = PageHelper.startPage(pageIndex, pageSize);
        this.baseMapper.getIdentityPage(request);
        PageResult<UserIdentityPageResponse> result = new PageResult<>(page);
        return result;
    }

    @Override
    public void identityExamine(UserExamineRequest request) {

        RLock lock = redissonClient.getLock(Constants.ADD_AUTH + request.getUserId());
        try {
            lock.lock();
            FrUserEntity frUserEntity = this.getById(request.getUserId());
            if (frUserEntity == null) {
                throw new JrsfException(BaseUserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
            }

            FrUserAuthService frUserAuthService = SpringUtils.getBean(FrUserAuthService.class);
            FrUserAuthEntity frUserAuthEntity = frUserAuthService.getUserAuth(request.getUserId(), AuthTypeEnum.IdentityAuth, AuthStatusEnum.EXAMINE);

            if (frUserAuthEntity != null && frUserAuthEntity.getId() != request.getVersion()) {
                throw new JrsfException(UserExceptionEnum.USER_VERSION_DIFFERENT_EXCEPTION);
            }

            if (frUserEntity.getAdditional() == null) {
                AdditionalResponse additional = new AdditionalResponse();
                additional.setIdentityAuth(request.getResult());
                frUserEntity.setAdditional(additional);
            } else {
                frUserEntity.getAdditional().setIdentityAuth(request.getResult());
            }
            this.updateById(frUserEntity);

            //添加审核记录
            addExamine(request, AuthTypeEnum.IdentityAuth);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public IdentityExamineInfoResponse identityExamine(Integer id) {
        IdentityExamineInfoResponse response = new IdentityExamineInfoResponse();
        FrUserEntity frUserEntity = this.baseMapper.selectById(id);
        if (frUserEntity != null) {
            BeanUtils.copyProperties(frUserEntity, response);
            if (frUserEntity.getAdditional() != null) {
                if (AuthStatusEnum.REFUSE.equals(frUserEntity.getAdditional().getIdentityAuth())) {
                    FrUserExamineEntity frUserExamineEntity = this.frUserExamineService.getExamine(id, AuthTypeEnum.IdentityAuth, AuthStatusEnum.REFUSE);
                    if (frUserExamineEntity != null) {
                        response.setRemarks(frUserExamineEntity.getRemarks());
                    }
                }
                response.setAuthStatus(frUserEntity.getAdditional().getIdentityAuth());
            }

            response.setConstellation(DateUtils.getConstellation(frUserEntity.getBirthday()));
            FrUserAuthService frUserAuthService = SpringUtils.getBean(FrUserAuthService.class);
            FrUserAuthEntity frUserAuthEntity = frUserAuthService.getUserAuth(id, AuthTypeEnum.IdentityAuth, AuthStatusEnum.EXAMINE);
            if (frUserAuthEntity != null) {
                response.setImgList(frUserAuthEntity.getImgList());
                response.setVersion(frUserAuthEntity.getId());
            } else {
                response.setImgList(null);
                response.setVersion(0);
            }
        }

        return response;
    }

    @Override
    public PageResult<UserEducationPageResponse> userEducationPage(UserPageRequest request, Integer pageIndex, Integer pageSize) {
        Page<UserEducationPageResponse> page = PageHelper.startPage(pageIndex, pageSize);
        this.baseMapper.getUserEducationPage(request);
        PageResult<UserEducationPageResponse> result = new PageResult<>(page);
        return result;
    }

    @Override
    public EducationExamineInfoResponse educationExamine(Integer id) {
        EducationExamineInfoResponse response = new EducationExamineInfoResponse();
        FrUserEntity frUserEntity = this.baseMapper.selectById(id);
        if (frUserEntity != null) {
            BeanUtils.copyProperties(frUserEntity, response);
            if (frUserEntity.getAdditional() != null) {
                if (AuthStatusEnum.REFUSE.equals(frUserEntity.getAdditional().getEducationAuth())) {
                    FrUserExamineEntity frUserExamineEntity = this.frUserExamineService.getExamine(id, AuthTypeEnum.EducationAuth, AuthStatusEnum.REFUSE);
                    if (frUserExamineEntity != null) {
                        response.setRemarks(frUserExamineEntity.getRemarks());
                    }
                }
                response.setAuthStatus(frUserEntity.getAdditional().getEducationAuth());
            }

            FrUserAuthService frUserAuthService = SpringUtils.getBean(FrUserAuthService.class);
            FrUserAuthEntity frUserAuthEntity = frUserAuthService.getUserAuth(id, AuthTypeEnum.EducationAuth, AuthStatusEnum.EXAMINE);
            if (frUserAuthEntity != null) {
                response.setImgList(frUserAuthEntity.getImgList());
                response.setVersion(frUserAuthEntity.getId());
            } else {
                response.setImgList(null);
                response.setVersion(0);
            }
            response.setConstellation(DateUtils.getConstellation(frUserEntity.getBirthday()));
        }

        return response;
    }

    @Override
    public void educationExamine(UserExamineRequest request) {

        RLock lock = redissonClient.getLock(Constants.ADD_AUTH + request.getUserId());
        try {
            lock.lock();
            FrUserEntity frUserEntity = this.getById(request.getUserId());
            if (frUserEntity == null) {
                throw new JrsfException(BaseUserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
            }

            FrUserAuthService frUserAuthService = SpringUtils.getBean(FrUserAuthService.class);
            FrUserAuthEntity frUserAuthEntity = frUserAuthService.getUserAuth(request.getUserId(), AuthTypeEnum.EducationAuth, AuthStatusEnum.EXAMINE);

            if (frUserAuthEntity != null && frUserAuthEntity.getId() != request.getVersion()) {
                throw new JrsfException(UserExceptionEnum.USER_VERSION_DIFFERENT_EXCEPTION);
            }

            if (frUserEntity.getAdditional() == null) {
                AdditionalResponse additional = new AdditionalResponse();
                additional.setEducationAuth(request.getResult());
                frUserEntity.setAdditional(additional);
            } else {
                frUserEntity.getAdditional().setEducationAuth(request.getResult());
            }
            this.updateById(frUserEntity);

            //添加审核记录
            addExamine(request, AuthTypeEnum.EducationAuth);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public PageResult<UserInfoPageResponse> userPage(UserInfoRequest request, Integer pageIndex, Integer pageSize) {
        if (StringUtils.isNotBlank(request.getNickName())) {
            request.setNickName("%" + request.getNickName() + "%");
        }

        if (StringUtils.isNotBlank(request.getCity())) {
            request.setCity("%" + request.getCity() + "%");
        }
        Page<UserInfoPageResponse> page = PageHelper.startPage(pageIndex, pageSize);
        this.baseMapper.getUserInfoPage(request);
        PageResult<UserInfoPageResponse> result = new PageResult<>(page);
        return result;
    }

    @Override
    public UserAdminInfoResponse userAdminInfo(Integer id) {

        UserAdminInfoResponse response = new UserAdminInfoResponse();
        FrUserEntity frUserEntity = this.baseMapper.selectById(id);
        if (frUserEntity == null) {
            throw new JrsfException(BaseUserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
        }
        if (frUserEntity.getAdditional() != null) {
            BeanUtils.copyProperties(frUserEntity.getAdditional(), response);
        }
        BeanUtils.copyProperties(frUserEntity, response);
        response.setConstellation(DateUtils.getConstellation(frUserEntity.getBirthday()));
        return response;
    }

    @Override
    @Transactional
    public void updateUser(UserAdminInfoAddRequest request) {

        if (request.getId() != null) {
            FrUserEntity frUserEntity = this.baseMapper.selectById(request.getId());
            if (frUserEntity == null) {
                throw new JrsfException(BaseUserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
            }

            if (!UserTypeEnum.Dummy.equals(frUserEntity.getUserType())) {
                throw new JrsfException(UserExceptionEnum.USER_NOT_DUMMY_EXCEPTION);
            }
            msfFileService.deleteImg(frUserEntity.getImgList(), request.getImgList());
        }
        FrUserEntity frUserEntity = new FrUserEntity();
        BeanUtils.copyProperties(request, frUserEntity);
        frUserEntity.setOpenId("");
        frUserEntity.setSessionKey("");
        if (CollectionUtil.isNotEmpty(request.getImgList())) {
            frUserEntity.setHeadPortrait(request.getImgList().get(0));
        }
        frUserEntity.setUserType(UserTypeEnum.Dummy);
        frUserEntity.setAuthStatus(AuthStatusEnum.PASS);
        AdditionalResponse additionalResponse = new AdditionalResponse();
        additionalResponse.setEducationAuth(AuthStatusEnum.PASS);
        additionalResponse.setIdentityAuth(AuthStatusEnum.PASS);
        frUserEntity.setAdditional(additionalResponse);
        saveOrUpdate(frUserEntity);
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        FrUserEntity frUserEntity = this.baseMapper.selectById(id);
        if (frUserEntity != null && !UserTypeEnum.Dummy.equals(frUserEntity.getUserType())) {
            throw new JrsfException(UserExceptionEnum.USER_NOT_DUMMY_EXCEPTION);
        }
        this.removeById(id);
    }

    @Override
    public OutlineResponse outline() {

        OutlineResponse outlineResponse = new OutlineResponse();

        //用户总数
        LambdaQueryWrapper<FrUserEntity> queryWrapper = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getUserType, UserTypeEnum.Normal);
        outlineResponse.setUserCount(count(queryWrapper));

        //今天注册
        Calendar calendar = Calendar.getInstance();
        LambdaQueryWrapper<FrUserEntity> todayRegisterQuery = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getUserType, UserTypeEnum.Normal).ge(FrUserEntity::getCreateTime, DateUtils.getStartTimeOfDay(calendar.getTime()));
        outlineResponse.setTodayRegister(count(todayRegisterQuery));

        //今日在线
        LambdaQueryWrapper<FrUserEntity> todayOnlineQuery = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getUserType, UserTypeEnum.Normal).ge(FrUserEntity::getLatelyTime, DateUtils.getStartTimeOfDay(calendar.getTime()));
        outlineResponse.setTodayOnline(count(todayOnlineQuery));

        //24小时注册
        calendar.add(Calendar.HOUR, -24);
        LambdaQueryWrapper<FrUserEntity> registerQuery = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getUserType, UserTypeEnum.Normal).ge(FrUserEntity::getCreateTime, calendar.getTime());
        outlineResponse.setRegister24(count(registerQuery));

        //24小时在线
        LambdaQueryWrapper<FrUserEntity> onlineQuery = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getUserType, UserTypeEnum.Normal).ge(FrUserEntity::getLatelyTime, calendar.getTime());
        outlineResponse.setOnline24(count(onlineQuery));

        //昨日注册
        LambdaQueryWrapper<FrUserEntity> registerYeardayQuery = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getUserType, UserTypeEnum.Normal).ge(FrUserEntity::getCreateTime, DateUtils.getStartTimeOfDay(calendar.getTime())).le(FrUserEntity::getCreateTime, DateUtils.getEndTimeOfDay(calendar.getTime()));
        outlineResponse.setYesterdayRegister(count(registerYeardayQuery));

        outlineResponse.setGenderPie(this.baseMapper.getGenderPie());
        outlineResponse.setCityBar(this.baseMapper.getCityBar());
        return outlineResponse;
    }

    @Override
    public FrontUserInfoResponse frontUserInfo(Integer id) {

        FrontUserInfoResponse response = new FrontUserInfoResponse();
        FrUserEntity frUserEntity = this.getById(id);
        if (frUserEntity == null) {
            throw new JrsfException(BaseUserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
        }
        BeanUtils.copyProperties(frUserEntity, response);
        response.setAge(DateUtils.getAgeByBirth(frUserEntity.getBirthday()));
        response.setConstellation(DateUtils.getConstellation(frUserEntity.getBirthday()));
        if (CollectionUtil.isEmpty(frUserEntity.getImgList())) {
            response.setImgList(ListUtil.toList(frUserEntity.getHeadPortrait()));
        }
        if (frUserEntity.getAdditional() != null) {
            if (AuthStatusEnum.PASS.equals(frUserEntity.getAdditional().getEducationAuth()) && AuthStatusEnum.PASS.equals(frUserEntity.getAdditional().getIdentityAuth())) {
                response.setUserAuthStatus(UserAuthStatusEnum.DOUBLE);
            } else if (AuthStatusEnum.PASS.equals(frUserEntity.getAdditional().getIdentityAuth())) {
                response.setUserAuthStatus(UserAuthStatusEnum.IdentityAuth);
            } else if (AuthStatusEnum.PASS.equals(frUserEntity.getAdditional().getEducationAuth())) {
                response.setUserAuthStatus(UserAuthStatusEnum.EducationAuth);
            }
        }
        Integer ownerId = TokenUtils.getOwnerId();
        if (ownerId == null) {
            ownerId = 0;
        }
        UserMatchingEntity selfResult = getUserMatch(ownerId, id);
        UserMatchingEntity otherResult = getUserMatch(id, ownerId);
        if (selfResult != null && otherResult != null && selfResult.getResult() && otherResult.getResult()) {
            response.setResult(true);
        } else {
            response.setResult(false);
        }
        if (selfResult != null) {
            if (selfResult.getResult()) {
                response.setSelfMatchingStatus(MatchingStatusEnum.LIKE);
            } else {
                response.setSelfMatchingStatus(MatchingStatusEnum.NOT_LIKE);
            }
        }
        if (otherResult != null) {
            if (otherResult.getResult()) {
                response.setOtherMatchingStatus(MatchingStatusEnum.LIKE_ME);
            } else {
                response.setOtherMatchingStatus(MatchingStatusEnum.NOT_LIKE_ME);
            }
        }
        response.setUserArticleViewResponse(getUserArticleView(response.getId()));

        return response;
    }

    @Override
    public List<ProportionResponse> userRegister(UserRegisterStatisticRequest request) {
        return this.baseMapper.getUserRegisterStatistic(request);
    }

    @Transactional
    @Override
    public void deleteFruser() {
        this.removeById(TokenUtils.getOwnerId());
    }

    @Override
    public PayMenuResponse payMenu() {

        PayMenuResponse payMenuResponse = new PayMenuResponse();
        FrUserEntity frUserEntity = this.getById(TokenUtils.getOwnerId());
        BeanUtils.copyProperties(frUserEntity, payMenuResponse);
        String menuValue = msfConfigService.getValueByCode(SysConfigCodeEnum.payMenuList.name());
        if (StringUtils.isNotBlank(menuValue)) {
            List<PayMoneyResponse> moneyResponseList = JSON.parseArray(menuValue, PayMoneyResponse.class);
            payMenuResponse.setPayMoneyList(moneyResponseList);
        }
        return payMenuResponse;
    }

    @Override
    public PayMenuResponse payMenuByCode(String code) {
        WxAppletOpenResponse wxAppletOpenResponse = wxAppletService.getOpenIdInfoByCode(code);
        PayMenuResponse payMenuResponse = new PayMenuResponse();
        String menuValue = msfConfigService.getValueByCode(SysConfigCodeEnum.payMenuList.name());
        if (StringUtils.isNotBlank(menuValue)) {
            List<PayMoneyResponse> moneyResponseList = JSON.parseArray(menuValue, PayMoneyResponse.class);
            payMenuResponse.setPayMoneyList(moneyResponseList);
        }
        if (wxAppletOpenResponse != null && StringUtils.isNotBlank(wxAppletOpenResponse.getUnionId())) {
            FrUserEntity frUserEntity = getFrUserByUnionId(wxAppletOpenResponse.getUnionId());
            if (frUserEntity == null) {
                throw new JrsfException(BaseUserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
            }
            BeanUtils.copyProperties(frUserEntity, payMenuResponse);
        }
        return payMenuResponse;
    }

    @Override
    public FrUserEntity getFrUserByUnionId(String unionId) {
        Wrapper<FrUserEntity> frUserEntityWrapper = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getUnionId, unionId);
        return getOne(frUserEntityWrapper);
    }

    @Override
    public ViewLikeMeResponse viewLikeMePrice() {

        ViewLikeMeResponse viewLikeMeResponse = new ViewLikeMeResponse();
        String value = msfConfigService.getValueByCode(SysConfigCodeEnum.viewlikeMe.name());
        if (StringUtils.isNotBlank(value)) {
            ViewLikeMeData viewLikeMeData = JSON.parseObject(value, ViewLikeMeData.class);
            BeanUtils.copyProperties(viewLikeMeData, viewLikeMeResponse);
        }
        return viewLikeMeResponse;
    }

    @Override
    @Transactional
    public void unlockLikeme(SeeLikeMeRequest request) {

        ViewLikeMeData viewLikeMeData = JSON.parseObject(msfConfigService.getValueByCode(SysConfigCodeEnum.viewlikeMe.name()), ViewLikeMeData.class);
        FrUserEntity frUserEntity = this.getById(TokenUtils.getOwnerId());
        if (request.getUserId() != null) {
            if (frUserEntity.getGoldBalance().compareTo(viewLikeMeData.getSingle()) < 0) {
                throw new JrsfException(UserExceptionEnum.BALANCE_LESS);
            }

            //用户解锁
            Wrapper<UserMatchingEntity> updateWrapper = new UpdateWrapper<UserMatchingEntity>().lambda().eq(UserMatchingEntity::getUserId, request.getUserId()).eq(UserMatchingEntity::getResult, true).eq(UserMatchingEntity::getOtherUser, TokenUtils.getOwnerId()).set(UserMatchingEntity::getIsUnlock, true);
            this.userMatchingService.update(updateWrapper);
            frUserEntity.setGoldBalance(frUserEntity.getGoldBalance() - viewLikeMeData.getSingle());
            this.updateById(frUserEntity);

        } else {
            //解锁全部
            Wrapper<UserMatchingEntity> countWrapper = new QueryWrapper<UserMatchingEntity>().lambda().eq(UserMatchingEntity::getResult, true).eq(UserMatchingEntity::getOtherUser, TokenUtils.getOwnerId()).and(wrapper -> wrapper.isNull(UserMatchingEntity::getIsUnlock).or().eq(UserMatchingEntity::getIsUnlock, false));
            Long count = this.userMatchingService.count(countWrapper);
            double price = (count * viewLikeMeData.getSingle()) * viewLikeMeData.getDiscount();
            BigDecimal priceDecimal = new BigDecimal(price);
            Integer priceInt = priceDecimal.intValue();
            if (priceInt.compareTo(viewLikeMeData.getSingle()) < 0) {
                throw new JrsfException(UserExceptionEnum.LIKE_ME_LESS_PRICE);
            }

            if (frUserEntity.getGoldBalance().compareTo(priceInt) < 0) {
                throw new JrsfException(UserExceptionEnum.BALANCE_LESS);
            }

            //解锁全部
            Wrapper<UserMatchingEntity> updateWrapper = new UpdateWrapper<UserMatchingEntity>().lambda().eq(UserMatchingEntity::getResult, true).eq(UserMatchingEntity::getOtherUser, TokenUtils.getOwnerId()).set(UserMatchingEntity::getIsUnlock, true);
            this.userMatchingService.update(updateWrapper);
            frUserEntity.setGoldBalance(frUserEntity.getGoldBalance() - priceInt);
            this.updateById(frUserEntity);

        }

    }

    private UserMatchingEntity getUserMatch(Integer ownerId, Integer id) {

        Wrapper<UserMatchingEntity> selfWrapper = new QueryWrapper<UserMatchingEntity>().lambda().eq(UserMatchingEntity::getUserId, ownerId).eq(UserMatchingEntity::getOtherUser, id).orderByDesc(UserMatchingEntity::getId);

        List<UserMatchingEntity> userMatchingEntityList = userMatchingService.getBaseMapper().selectList(selfWrapper);
        if (CollectionUtil.isNotEmpty(userMatchingEntityList)) {
            return userMatchingEntityList.get(0);
        }
        return null;
    }

    private String getCharacterName(CharacterTypeResponse characterType) {
        String name = "";
        if (characterType != null) {
            if (characterType.getExtroversionScore() != null) {
                name += (CharacterPosition.LEFT.equals(characterType.getExtroversionScore().getPosition()) ? "E" : "I");
            }

            if (characterType.getImagineScore() != null) {
                name += (CharacterPosition.LEFT.equals(characterType.getImagineScore().getPosition()) ? "N" : "S");
            }

            if (characterType.getPerceptualScore() != null) {
                name += (CharacterPosition.LEFT.equals(characterType.getPerceptualScore().getPosition()) ? "F" : "T");
            }

            if (characterType.getPlanScore() != null) {
                name += (CharacterPosition.LEFT.equals(characterType.getPlanScore().getPosition()) ? "J" : "P");
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("INTP", "思绪飞扬的学者");
        map.put("INTJ", "独立自主的专家");
        map.put("ENTP", "大雄的机器猫");
        map.put("ENTJ", "天生的领导者");
        map.put("ENFJ", "谆谆教诲的教育家");
        map.put("INFJ", "精神世界的引路人");
        map.put("INFP", "完美主义的知心人");
        map.put("ENFP", "追梦人");
        map.put("ESFP", "不可或缺的大活宝");
        map.put("ISFP", "静美的艺术家");
        map.put("ISTP", "谦逊的手艺人");
        map.put("ESTP", "活在当下的践行者");
        map.put("ISFJ", "具奉献精神的保护者");
        map.put("ISTJ", "一丝不苟的检查者");
        map.put("ESFJ", "盛情难却的东道主");
        map.put("ESTJ", "天生的管理者");

        name += ("-" + map.get(name));
        return name;
    }


    private CharacterResponse getCharacter(Integer score) {

        CharacterResponse characterResponse = new CharacterResponse();
        characterResponse.setScore(score);
        characterResponse.setPosition(score >= 5 ? CharacterPosition.LEFT : CharacterPosition.RIGHT);
        return characterResponse;

    }

    private Integer getRatio(FrUserEntity frUserEntity) {

        BigDecimal total = new BigDecimal("5");
        BigDecimal completion = new BigDecimal("0");

        if (frUserEntity.getMarriage() != null) {
            completion = completion.add(new BigDecimal("1"));
        }

        if (frUserEntity.getLoveGoal() != null) {
            completion = completion.add(new BigDecimal("1"));
        }

        if (frUserEntity.getEmotional() != null) {
            completion = completion.add(new BigDecimal("1"));
        }

        if (frUserEntity.getAnnualSalary() != null) {
            completion = completion.add(new BigDecimal("1"));
        }

        if (frUserEntity.getCharacterType() != null) {
            completion = completion.add(new BigDecimal("1"));
        }

        return completion.divide(total).multiply(new BigDecimal("100")).setScale(0, RoundingMode.DOWN).intValue();
    }

    private RecommendUserInfoResponse getRecommendUserInfoByParam(Map<String, Object> param) {

        param.put("size", 1);
        List<RecommendUserInfoResponse> list = this.baseMapper.getRecommendUserInfo(param);
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(model -> {
                model.setAge(DateUtils.getAgeByBirth(model.getBirthday()));
                model.setConstellation(DateUtils.getConstellation(model.getBirthday()));
                if (model.getAdditional() != null) {
                    if (AuthStatusEnum.PASS.equals(model.getAdditional().getEducationAuth()) && AuthStatusEnum.PASS.equals(model.getAdditional().getIdentityAuth())) {
                        model.setUserAuthStatus(UserAuthStatusEnum.DOUBLE);
                    } else if (AuthStatusEnum.PASS.equals(model.getAdditional().getEducationAuth())) {
                        model.setUserAuthStatus(UserAuthStatusEnum.EducationAuth);
                    } else if (AuthStatusEnum.PASS.equals(model.getAdditional().getIdentityAuth())) {
                        model.setUserAuthStatus(UserAuthStatusEnum.IdentityAuth);
                    }
                }
            });
            return list.get(0);
        }
        return null;
    }
}
