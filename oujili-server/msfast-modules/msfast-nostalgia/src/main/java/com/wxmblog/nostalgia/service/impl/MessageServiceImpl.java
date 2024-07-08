package com.wxmblog.nostalgia.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wxmblog.base.common.utils.DateUtils;
import com.wxmblog.base.common.utils.SpringUtils;
import com.wxmblog.base.common.utils.TokenUtils;
import com.wxmblog.nostalgia.common.enums.article.MessageCategoryEnum;
import com.wxmblog.nostalgia.common.enums.article.MessageStatus;
import com.wxmblog.nostalgia.common.rest.request.message.MessageAddRequest;
import com.wxmblog.nostalgia.common.rest.response.front.article.ArticlePageResponse;
import com.wxmblog.nostalgia.common.rest.response.front.article.MessagePageResponse;
import com.wxmblog.nostalgia.dao.ArticleCommentDao;
import com.wxmblog.nostalgia.dao.ArticleDao;
import com.wxmblog.nostalgia.dao.CommentReplyDao;
import com.wxmblog.nostalgia.entity.ArticleCommentEntity;
import com.wxmblog.nostalgia.entity.ArticleEntity;
import com.wxmblog.nostalgia.entity.CommentReplyEntity;
import com.wxmblog.nostalgia.service.ArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxmblog.nostalgia.dao.MessageDao;
import com.wxmblog.nostalgia.entity.MessageEntity;
import com.wxmblog.nostalgia.service.MessageService;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Service("articleMessageService")
public class MessageServiceImpl extends ServiceImpl<MessageDao, MessageEntity> implements MessageService {

    @Autowired
    ArticleDao articleDao;

    @Autowired
    ArticleCommentDao articleCommentDao;

    @Autowired
    CommentReplyDao commentReplyDao;

    @Transactional
    public void addMessage(MessageAddRequest request) {
        MessageEntity messageEntity = new MessageEntity();
        BeanUtils.copyProperties(request, messageEntity);
        messageEntity.setStatus(MessageStatus.UNREAD);
        this.baseMapper.insert(messageEntity);
    }

    @Override
    public void addCommentMessage(ArticleCommentEntity commentEntity, Integer ownerId) {
        MessageAddRequest messageAddRequest = new MessageAddRequest();
        messageAddRequest.setSenderId(ownerId);
        messageAddRequest.setContent(commentEntity.getContent());
        messageAddRequest.setCategory(MessageCategoryEnum.COMMENT);
        messageAddRequest.setReplyId(null);
        messageAddRequest.setArticleId(commentEntity.getArticleId());
        messageAddRequest.setArticleCommentId(commentEntity.getId());
        messageAddRequest.setCommentReplyId(null);
        ArticleEntity articleEntity = articleDao.selectById(commentEntity.getArticleId());
        if (articleEntity != null) {
            messageAddRequest.setUserId(articleEntity.getUserId());
            messageAddRequest.setArticleContent(articleEntity.getContent());
            messageAddRequest.setArticleImg(articleEntity.getImg());
        }
        addMessage(messageAddRequest);
    }

    @Override
    public void addCommentReplyMessage(CommentReplyEntity commentReplyEntity, Integer ownerId) {
        MessageAddRequest messageAddRequest = new MessageAddRequest();
        messageAddRequest.setSenderId(ownerId);

        messageAddRequest.setContent(commentReplyEntity.getContent());
        messageAddRequest.setCategory(MessageCategoryEnum.REPLY);
        Set<Integer> userIds = new HashSet<>();
        ArticleCommentEntity articleCommentEntity = articleCommentDao.selectById(commentReplyEntity.getArticleCommentId());
        if (commentReplyEntity.getCommentReplyId() == null) {
            //回复的评论 通知评论者
            if (articleCommentEntity != null) {
                messageAddRequest.setReplyId(articleCommentEntity.getUserId());
                userIds.add(articleCommentEntity.getUserId());
            }

        } else {
            //回复的回复 通知 回复者
            CommentReplyEntity replyEntity = commentReplyDao.selectById(commentReplyEntity.getCommentReplyId());
            if (replyEntity != null) {
                messageAddRequest.setReplyId(replyEntity.getUserId());
                userIds.add(replyEntity.getUserId());
            }
        }

        messageAddRequest.setArticleId(articleCommentEntity.getArticleId());
        messageAddRequest.setArticleCommentId(commentReplyEntity.getArticleCommentId());
        messageAddRequest.setCommentReplyId(commentReplyEntity.getId());
        ArticleEntity articleEntity = articleDao.selectById(articleCommentEntity.getArticleId());
        if (articleEntity != null) {
            messageAddRequest.setArticleContent(articleEntity.getContent());
            messageAddRequest.setArticleImg(articleEntity.getImg());
            userIds.add(articleEntity.getUserId());
        }
        userIds.forEach(userId -> {
            messageAddRequest.setUserId(userId);
            addMessage(messageAddRequest);
        });
    }

    @Override
    public void addArticlePraiseMessage(ArticleEntity articleEntity, Integer ownerId) {

        MessageAddRequest messageAddRequest = new MessageAddRequest();
        messageAddRequest.setSenderId(ownerId);
        messageAddRequest.setContent(null);
        messageAddRequest.setCategory(MessageCategoryEnum.LIKE_ARTICLE);
        messageAddRequest.setReplyId(null);
        messageAddRequest.setArticleId(articleEntity.getId());
        messageAddRequest.setArticleCommentId(null);
        messageAddRequest.setCommentReplyId(null);
        if (articleEntity != null) {
            messageAddRequest.setUserId(articleEntity.getUserId());
            messageAddRequest.setArticleContent(articleEntity.getContent());
            messageAddRequest.setArticleImg(articleEntity.getImg());
        }
        addMessage(messageAddRequest);
    }

    @Override
    public void addCommenePraiseMessage(ArticleCommentEntity commentEntity, Integer ownerId) {

        Set<Integer> userIds = new HashSet<>();

        MessageAddRequest messageAddRequest = new MessageAddRequest();
        messageAddRequest.setSenderId(ownerId);
        messageAddRequest.setContent(commentEntity.getContent());
        messageAddRequest.setCategory(MessageCategoryEnum.LIKE_COMMENT);
        messageAddRequest.setReplyId(commentEntity.getUserId());
        messageAddRequest.setArticleId(commentEntity.getArticleId());
        messageAddRequest.setArticleCommentId(commentEntity.getId());
        messageAddRequest.setCommentReplyId(null);
        userIds.add(commentEntity.getUserId());
        ArticleEntity articleEntity = articleDao.selectById(commentEntity.getArticleId());
        if (articleEntity != null) {
            userIds.add(articleEntity.getUserId());
            messageAddRequest.setArticleContent(articleEntity.getContent());
            messageAddRequest.setArticleImg(articleEntity.getImg());
        }
        userIds.forEach(userId -> {
            messageAddRequest.setUserId(userId);
            addMessage(messageAddRequest);
        });
    }

    @Override
    public void addReplyPraiseMessage(CommentReplyEntity replyEntity, Integer ownerId) {
        Set<Integer> userIds = new HashSet<>();

        MessageAddRequest messageAddRequest = new MessageAddRequest();
        messageAddRequest.setSenderId(ownerId);
        messageAddRequest.setContent(replyEntity.getContent());
        messageAddRequest.setCategory(MessageCategoryEnum.LIKE_REPLY);
        messageAddRequest.setReplyId(replyEntity.getUserId());
        userIds.add(replyEntity.getUserId());
        ArticleCommentEntity commentEntity = articleCommentDao.selectById(replyEntity.getArticleCommentId());
        if (commentEntity != null) {
            messageAddRequest.setArticleId(commentEntity.getArticleId());
            userIds.add(commentEntity.getUserId());

            ArticleEntity articleEntity = articleDao.selectById(commentEntity.getArticleId());
            if (articleEntity != null) {
                userIds.add(articleEntity.getUserId());
                messageAddRequest.setArticleContent(articleEntity.getContent());
                messageAddRequest.setArticleImg(articleEntity.getImg());
            }
        }
        messageAddRequest.setArticleCommentId(replyEntity.getArticleCommentId());
        messageAddRequest.setCommentReplyId(replyEntity.getId());

        userIds.forEach(userId -> {
            messageAddRequest.setUserId(userId);
            addMessage(messageAddRequest);
        });
    }

    @Override
    public Page<MessagePageResponse> getMessagePage(Integer pageIndex, Integer pageSize) {
        Page<MessagePageResponse> page = PageHelper.startPage(pageIndex, pageSize);
        this.getBaseMapper().getMessagePage(TokenUtils.getOwnerId());
        page.forEach(model -> {
            model.setCreateTimeDesc(DateUtils.getChineseTime(model.getCreateTime()));
        });
        ArticleService articleService = SpringUtils.getBean(ArticleService.class);
        articleService.readMessage(TokenUtils.getOwnerId());
        return page;
    }

    @Override
    public Long unread() {
        Integer ownerId = TokenUtils.getOwnerId();
        if (ownerId != null) {
            Wrapper<MessageEntity> wrapper = new QueryWrapper<MessageEntity>().lambda()
                    .eq(MessageEntity::getUserId, ownerId)
                    .eq(MessageEntity::getStatus, MessageStatus.UNREAD);
            return this.getBaseMapper().selectCount(wrapper);
        }
        return null;
    }
}
