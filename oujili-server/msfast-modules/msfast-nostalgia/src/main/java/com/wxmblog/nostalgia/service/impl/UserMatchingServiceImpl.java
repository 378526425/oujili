package com.wxmblog.nostalgia.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wxmblog.base.auth.service.MsfConfigService;
import com.wxmblog.base.common.enums.BaseUserExceptionEnum;
import com.wxmblog.base.common.enums.FrUserStatusEnum;
import com.wxmblog.base.common.enums.GenderEnum;
import com.wxmblog.base.common.exception.JrsfException;
import com.wxmblog.base.common.utils.DateUtils;
import com.wxmblog.base.common.utils.PageResult;
import com.wxmblog.base.common.utils.SpringUtils;
import com.wxmblog.base.common.utils.TokenUtils;
import com.wxmblog.base.websocket.common.enums.MessageFormatEnum;
import com.wxmblog.base.websocket.common.rest.request.BaseMessageInfo;
import com.wxmblog.base.websocket.service.MsFastMessageService;
import com.wxmblog.nostalgia.common.constant.Constants;
import com.wxmblog.nostalgia.common.enums.user.SysConfigCodeEnum;
import com.wxmblog.nostalgia.common.enums.user.UserTypeEnum;
import com.wxmblog.nostalgia.common.exception.UserExceptionEnum;
import com.wxmblog.nostalgia.common.rest.request.fruser.ChoiceRequest;
import com.wxmblog.nostalgia.common.rest.response.front.matching.LikeMePageResponse;
import com.wxmblog.nostalgia.common.rest.response.front.matching.LikePageResponse;
import com.wxmblog.nostalgia.common.rest.response.front.matching.MatchingResponse;
import com.wxmblog.nostalgia.common.rest.response.front.matching.SuccessPageResponse;
import com.wxmblog.nostalgia.dao.UserMatchingDao;
import com.wxmblog.nostalgia.entity.FrUserEntity;
import com.wxmblog.nostalgia.entity.UserMatchingEntity;
import com.wxmblog.nostalgia.service.FrUserService;
import com.wxmblog.nostalgia.service.UserMatchingService;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;


@Service("userMatchingService")
public class UserMatchingServiceImpl extends ServiceImpl<UserMatchingDao, UserMatchingEntity> implements UserMatchingService {

    @Autowired
    MsfConfigService msfConfigService;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    MsFastMessageService msFastMessageService;

    @Autowired
    DataSourceTransactionManager transactionManager;

    @Override
    public Long matchingNum() {

        Calendar calendar = Calendar.getInstance();
        Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
        QueryWrapper<UserMatchingEntity> wrapper = new QueryWrapper<UserMatchingEntity>();
        wrapper.lambda().eq(UserMatchingEntity::getUserId, TokenUtils.getOwnerId());
        if (hour < 12) {
            Calendar calendarLast = Calendar.getInstance();
            Date yesterday = new Date(DateUtils.getStartTimeOfDay(calendar.getTime()).getTime() - 1);
            calendarLast.setTime(yesterday);
            String yesterStr = calendarLast.get(Calendar.YEAR) + "-" + (calendarLast.get(Calendar.MONTH) + 1) + "-" + calendarLast.get(Calendar.DATE) + " 12:00:00";
            //前一天12点
            Date yesterdayNoon = DateUtils.parseDate(yesterStr);
            wrapper.apply("create_time>0={0} and create_time<{1}", yesterdayNoon, DateUtils.getEndTimeOfDay(calendar.getTime()));
            //wrapper.apply("create_time>={0} and create_time<{1}", yesterdayNoon, calendar.getTime());
        } else {
            String strTodayNoon = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE) + " 12:00:00";
            Date todayNoon = DateUtils.parseDate(strTodayNoon);
            //wrapper.apply("create_time>={0} and create_time<{1}", todayNoon, calendar.getTime());
            wrapper.apply("create_time>={0} and create_time<{1}", todayNoon, DateUtils.getEndTimeOfDay(calendar.getTime()));
        }
        return this.baseMapper.selectCount(wrapper);
    }

    @Override
    @Transactional
    public MatchingResponse match(ChoiceRequest request) {
        MatchingResponse matchingResponse;
        RLock lock = redissonClient.getLock(Constants.MATCHING + TokenUtils.getOwnerId());
        try {
            lock.lock();
            matchingResponse = matchUser(request);
        } finally {
            lock.unlock();
        }
        return matchingResponse;
    }

    MatchingResponse matchUser(ChoiceRequest request) {

        MatchingResponse matchingResponse = new MatchingResponse(false);
        Integer userId = TokenUtils.getOwnerId();
        FrUserService frUserService = SpringUtils.getBean(FrUserService.class);
        FrUserEntity frUserEntity = frUserService.getById(userId);
        if (frUserEntity == null) {
            throw new JrsfException(BaseUserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
        }

        if (!FrUserStatusEnum.ENABLE.equals(frUserEntity.getStatus())) {
            throw new JrsfException(BaseUserExceptionEnum.USER_STATUS_ERROR_EXCEPTION);
        }

        Integer num = Integer.valueOf(msfConfigService.getValueByCode(SysConfigCodeEnum.recommendTotal.name()));
        Integer count = Integer.parseInt(this.matchingNum().toString());
        if (num.compareTo(count) <= 0) {
            throw new JrsfException(UserExceptionEnum.MATCHING_BEYOND_LIMIT_EXCEPTION);
        }


        Wrapper<UserMatchingEntity> wrapper = new QueryWrapper<UserMatchingEntity>().lambda()
                .eq(UserMatchingEntity::getUserId, userId)
                .eq(UserMatchingEntity::getOtherUser, request.getOtherUser());
        Long matchingCount = this.baseMapper.selectCount(wrapper);
        if (matchingCount == 0) {
            UserMatchingEntity userMatchingEntity = new UserMatchingEntity();
            BeanUtils.copyProperties(request, userMatchingEntity);
            userMatchingEntity.setUserId(userId);

            if (userMatchingEntity.getResult()) {
                int random = RandomUtil.randomInt(0, 3);
                if (frUserEntity != null) {
                    String gender = GenderEnum.MALE.equals(frUserEntity.getGender()) ? "小哥哥" : "小姐姐";
                    switch (random) {
                        case 0:
                            if (StringUtils.isNotBlank(frUserEntity.getCity())) {
                                userMatchingEntity.setDescInfo("一个" + frUserEntity.getCity() + "的" + gender);
                            }
                            break;
                        case 1:
                            userMatchingEntity.setDescInfo("一个" + DateUtils.getConstellation(frUserEntity.getBirthday()) + "的" + gender);
                            break;
                        default:
                            if (frUserEntity.getBirthday() != null) {
                                userMatchingEntity.setDescInfo("一个" + DateUtils.getAgeByBirth(frUserEntity.getBirthday()) + "岁的" + gender);
                            }
                    }

                    //判断对方是否也喜欢我了
                    matchingResponse.setSelfId(frUserEntity.getId());
                    matchingResponse.setSelfHeadPortrait(frUserEntity.getHeadPortrait());
                    matchingResponse.setSelfNickName(frUserEntity.getNickName());

                    Wrapper<UserMatchingEntity> wrapperOther = new QueryWrapper<UserMatchingEntity>().lambda()
                            .eq(UserMatchingEntity::getUserId, request.getOtherUser())
                            .eq(UserMatchingEntity::getOtherUser, userId)
                            .eq(UserMatchingEntity::getResult, true);
                    Long matchingOtherCount = this.baseMapper.selectCount(wrapperOther);
                    FrUserEntity otherUser = frUserService.getById(request.getOtherUser());
                    if (otherUser != null) {
                        if (matchingOtherCount > 0 || (UserTypeEnum.Dummy.equals(otherUser.getUserType())&&random<1)) {
                            matchingResponse.setResult(true);
                            matchingResponse.setOtherHeadPortrait(otherUser.getHeadPortrait());
                            matchingResponse.setOtherId(otherUser.getId());
                            matchingResponse.setOtherNickName(otherUser.getNickName());
                            //添加对话框
                            addMessage(userId, request.getOtherUser());
                            if (UserTypeEnum.Dummy.equals(otherUser.getUserType())) {
                                UserMatchingEntity userMatchingEntity1 = new UserMatchingEntity();
                                userMatchingEntity1.setUserId(otherUser.getId());
                                userMatchingEntity1.setOtherUser(userId);
                                String genderOther = GenderEnum.MALE.equals(otherUser.getGender()) ? "小哥哥" : "小姐姐";
                                userMatchingEntity1.setDescInfo("一个" + DateUtils.getConstellation(otherUser.getBirthday()) + "的" + genderOther);
                                userMatchingEntity1.setResult(true);
                                this.baseMapper.insert(userMatchingEntity1);
                            }
                        }
                    }

                }

            }
            this.baseMapper.insert(userMatchingEntity);
        }
        return matchingResponse;
    }

    @Async
    void addMessage(Integer userId, Integer otherUser) {
        BaseMessageInfo baseMessageInfo = new BaseMessageInfo();
        baseMessageInfo.setContent("配对成功");
        baseMessageInfo.setMessageFormat(MessageFormatEnum.text.name());
        msFastMessageService.addMessageList(baseMessageInfo, userId, otherUser);
        msFastMessageService.addMessageList(baseMessageInfo, otherUser, userId);

    }

    @Override
    public PageResult<LikeMePageResponse> likeMePage(Integer pageIndex, Integer pageSize) {
        Page<LikeMePageResponse> page = PageHelper.startPage(pageIndex, pageSize);
        this.getBaseMapper().getLikeMePage(TokenUtils.getOwnerId());
        PageResult<LikeMePageResponse> result = new PageResult<>(page);
        return result;
    }

    @Override
    public PageResult<SuccessPageResponse> successPage(Integer pageIndex, Integer pageSize) {
        Page<SuccessPageResponse> page = PageHelper.startPage(pageIndex, pageSize);
        this.getBaseMapper().getSuccessPage(TokenUtils.getOwnerId());
        PageResult<SuccessPageResponse> result = new PageResult<>(page);
        result.getRows().forEach(model -> {
            if (model.getBirthday() != null) {
                model.setAge(DateUtils.getAgeByBirth(model.getBirthday()));
            }
        });
        return result;
    }

    @Override
    public PageResult<LikePageResponse> likePage(Integer pageIndex, Integer pageSize) {
        Page<LikePageResponse> page = PageHelper.startPage(pageIndex, pageSize);
        this.getBaseMapper().getLikePage(TokenUtils.getOwnerId());
        PageResult<LikePageResponse> result = new PageResult<>(page);
        result.getRows().forEach(model -> {
            if (model.getBirthday() != null) {
                model.setAge(DateUtils.getAgeByBirth(model.getBirthday()));
            }
        });
        return result;
    }

    @Override
    @Transactional
    public void cancelMatch(Integer id) {

        Wrapper<UserMatchingEntity> wrapper = new QueryWrapper<UserMatchingEntity>().lambda()
                .eq(UserMatchingEntity::getUserId, TokenUtils.getOwnerId())
                .eq(UserMatchingEntity::getOtherUser, id);
        this.remove(wrapper);

    }
}
