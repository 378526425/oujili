package com.wxmblog.nostalgia.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wxmblog.base.auth.service.MsfConfigService;
import com.wxmblog.base.common.enums.BaseExceptionEnum;
import com.wxmblog.base.common.exception.JrsfException;
import com.wxmblog.base.common.utils.DateUtils;
import com.wxmblog.base.common.utils.PageResult;
import com.wxmblog.base.common.utils.TokenUtils;
import com.wxmblog.base.file.service.MsfFileService;
import com.wxmblog.nostalgia.common.constant.Constants;
import com.wxmblog.nostalgia.common.enums.article.ArticleTabEnum;
import com.wxmblog.nostalgia.common.enums.article.MessageStatus;
import com.wxmblog.nostalgia.common.enums.article.PraiseTypeEnum;
import com.wxmblog.nostalgia.common.enums.user.HighestEducationEnum;
import com.wxmblog.nostalgia.common.exception.UserExceptionEnum;
import com.wxmblog.nostalgia.common.rest.request.article.ArticleAddRequest;
import com.wxmblog.nostalgia.common.rest.request.article.ArticlePageRequest;
import com.wxmblog.nostalgia.common.rest.request.article.PraiseRequest;
import com.wxmblog.nostalgia.common.rest.response.front.article.ArticlePageResponse;
import com.wxmblog.nostalgia.dao.*;
import com.wxmblog.nostalgia.entity.*;
import com.wxmblog.nostalgia.service.ArticleService;
import com.wxmblog.nostalgia.service.MessageService;
import com.wxmblog.nostalgia.service.RecommendConfigService;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleEntity> implements ArticleService {

    @Autowired
    private ArticleCommentDao articleCommentDao;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    private CommentReplyDao commentReplyDao;

    @Autowired
    RecommendConfigService recommendConfigService;

    @Autowired
    FrUserDao frUserDao;

    @Autowired
    UserToLikeDao userToLikeDao;

    @Autowired
    ArticleBackgroundDao articleBackgroundDao;

    @Autowired
    MsfConfigService msfConfigService;

    @Autowired
    MsfFileService fileService;

    @Autowired
    MessageService messageService;

    @Transactional
    @Override
    public void addArticle(ArticleAddRequest request) {

        if (StringUtils.isBlank(request.getContent()) && CollectionUtil.isEmpty(request.getImg())) {
            throw new JrsfException(UserExceptionEnum.ARTICLE_IS_EMPTY);
        }

        ArticleEntity article = new ArticleEntity();
        BeanUtils.copyProperties(request, article);
        article.setUserId(TokenUtils.getOwnerId());
        this.baseMapper.insert(article);
    }

    @Override
    public PageResult<ArticlePageResponse> articlePage(ArticlePageRequest request, Integer pageIndex, Integer pageSize) {

        request.setUserId(TokenUtils.getOwnerId());
        if (TokenUtils.getOwnerId() == null && !ArticleTabEnum.RECOMMEND.equals(request.getTabType())) {
            throw new JrsfException(BaseExceptionEnum.NO_LOGIN_EXCEPTION);
        }
        if (ArticleTabEnum.CITY.equals(request.getTabType())) {

            RecommendConfigEntity recommendConfigEntity = this.recommendConfigService.getRecommendConfigByUserId(TokenUtils.getOwnerId());
            if (recommendConfigEntity != null && CollectionUtil.isNotEmpty(recommendConfigEntity.getCity())) {
                request.setMyCity(recommendConfigEntity.getCity());
            } else {
                FrUserEntity frUserEntity = frUserDao.selectById(TokenUtils.getOwnerId());
                if (frUserEntity != null && StringUtils.isNotBlank(frUserEntity.getCity())) {
                    List<String> city = new ArrayList<>();
                    city.add(frUserEntity.getCity());
                    request.setMyCity(city);
                }
            }
        }

        Page<ArticlePageResponse> page = PageHelper.startPage(pageIndex, pageSize);
        this.getBaseMapper().getArticlePage(request);
        PageResult<ArticlePageResponse> result = new PageResult<>(page);
        result.getRows().forEach(model -> {
            if (StringUtils.isNotBlank(model.getInformation())) {
                HighestEducationEnum[] educationEnums = HighestEducationEnum.values();
                for (int i = 0; i < educationEnums.length; i++) {
                    model.setInformation(model.getInformation().replace(educationEnums[i].name(), educationEnums[i].getDesc()));
                }

            }
            model.setTime(DateUtils.getChineseTime(model.getCreateTime()));
            model.setCreateTime(null);
            model.setIsDelete(TokenUtils.getOwnerId()!=null&&model.getUserId().compareTo(TokenUtils.getOwnerId()) == 0);
        });
        return result;
    }

    private void addCommentCount(Integer articleId) {
        RLock lock = redissonClient.getLock(Constants.ADD_COMMENT + articleId);
        try {
            lock.lock();
            ArticleEntity articleEntity = this.baseMapper.selectById(articleId);
            if (articleEntity != null) {
                articleEntity.setCommentCount(articleEntity.getCommentCount() + 1);
            }
            this.baseMapper.updateById(articleEntity);
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    @Override
    public Long praise(PraiseRequest request) {
        Wrapper<UserToLikeEntity> wrapper = new QueryWrapper<UserToLikeEntity>().lambda().eq(UserToLikeEntity::getUserId, TokenUtils.getOwnerId()).eq(UserToLikeEntity::getTargetId, request.getId()).eq(UserToLikeEntity::getPraiseType, request.getPraiseType());
        Long praiseCount = this.userToLikeDao.selectCount(wrapper);
        if (praiseCount == 0) {
            UserToLikeEntity userToLikeEntity = new UserToLikeEntity();
            userToLikeEntity.setPraiseType(request.getPraiseType());
            userToLikeEntity.setTargetId(request.getId());
            userToLikeEntity.setUserId(TokenUtils.getOwnerId());
            userToLikeDao.insert(userToLikeEntity);
        }

        Wrapper<UserToLikeEntity> wrapperTotal = new QueryWrapper<UserToLikeEntity>().lambda().eq(UserToLikeEntity::getTargetId, request.getId()).eq(UserToLikeEntity::getPraiseType, request.getPraiseType());
        Long praiseTotal = this.userToLikeDao.selectCount(wrapperTotal);
        updatePraise(request, Integer.parseInt(praiseTotal.toString()));
        return praiseTotal;
    }

    private void updatePraise(PraiseRequest request, Integer praiseTotal) {

        RLock lock = redissonClient.getLock(Constants.PRAISE + request.getPraiseType().name() + request.getId());
        try {
            lock.lock();
            if (PraiseTypeEnum.ARTICLE.equals(request.getPraiseType())) {

                ArticleEntity articleEntity = this.baseMapper.selectById(request.getId());
                if (articleEntity != null) {
                    articleEntity.setLikeCount(praiseTotal);
                }
                this.baseMapper.updateById(articleEntity);

                messageService.addArticlePraiseMessage(articleEntity, TokenUtils.getOwnerId());
            } else if (PraiseTypeEnum.COMMENT.equals(request.getPraiseType())) {
                ArticleCommentEntity commentEntity = articleCommentDao.selectById(request.getId());
                if (commentEntity != null) {
                    commentEntity.setLikeCount(praiseTotal);
                }
                articleCommentDao.updateById(commentEntity);
                messageService.addCommenePraiseMessage(commentEntity, TokenUtils.getOwnerId());
            } else if (PraiseTypeEnum.REPLY.equals(request.getPraiseType())) {
                CommentReplyEntity replyEntity = commentReplyDao.selectById(request.getId());
                if (replyEntity != null) {
                    replyEntity.setLikeCount(praiseTotal);
                }
                commentReplyDao.updateById(replyEntity);
                messageService.addReplyPraiseMessage(replyEntity, TokenUtils.getOwnerId());
            }

        } finally {
            lock.unlock();
        }
    }

    @Transactional
    @Override
    public Long cancelPraise(PraiseRequest request) {
        Wrapper<UserToLikeEntity> wrapper = new QueryWrapper<UserToLikeEntity>().lambda().eq(UserToLikeEntity::getUserId, TokenUtils.getOwnerId()).eq(UserToLikeEntity::getTargetId, request.getId()).eq(UserToLikeEntity::getPraiseType, request.getPraiseType());
        this.userToLikeDao.delete(wrapper);

        Wrapper<UserToLikeEntity> wrapperTotal = new QueryWrapper<UserToLikeEntity>().lambda().eq(UserToLikeEntity::getTargetId, request.getId()).eq(UserToLikeEntity::getPraiseType, request.getPraiseType());
        Long praiseTotal = this.userToLikeDao.selectCount(wrapperTotal);
        updatePraise(request, Integer.parseInt(praiseTotal.toString()));
        return praiseTotal;
    }



    private String formatNumberWithW(Long number) {

        if (number < 10000) {
            return String.valueOf(number);
        }
        // 将数值转换为以万为单位的数值
        double convertedNumber = number / 10000.0;
        BigDecimal bigDecimal = new BigDecimal(convertedNumber);

        return bigDecimal.setScale(1, BigDecimal.ROUND_DOWN).toString() + "w";
    }

    @Transactional
    @Override
    public void deleteArticle(Integer id) {

        ArticleEntity articleEntity = this.getBaseMapper().selectById(id);
        if (articleEntity == null) {
            throw new JrsfException(BaseExceptionEnum.UNKNOWN_EXCEPTION).setMsg("动态不存在");
        }

        if (TokenUtils.getOwnerId().compareTo(articleEntity.getUserId()) != 0) {
            throw new JrsfException(BaseExceptionEnum.UNKNOWN_EXCEPTION).setMsg("没有权限");
        }
        this.getBaseMapper().deleteById(id);
    }

    @Transactional
    @Override
    public void readMessage(Integer ownerId) {
        Wrapper<MessageEntity> wrapper = new UpdateWrapper<MessageEntity>().lambda()
                .eq(MessageEntity::getUserId, ownerId)
                .set(MessageEntity::getStatus, MessageStatus.READ);
        messageService.update(wrapper);
    }
}
