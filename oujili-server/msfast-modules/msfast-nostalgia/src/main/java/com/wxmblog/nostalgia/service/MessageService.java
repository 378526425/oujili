package com.wxmblog.nostalgia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.wxmblog.nostalgia.common.rest.request.message.MessageAddRequest;
import com.wxmblog.nostalgia.common.rest.response.front.article.MessagePageResponse;
import com.wxmblog.nostalgia.entity.ArticleCommentEntity;
import com.wxmblog.nostalgia.entity.ArticleEntity;
import com.wxmblog.nostalgia.entity.CommentReplyEntity;
import com.wxmblog.nostalgia.entity.MessageEntity;
import org.springframework.scheduling.annotation.Async;


/**
 * 备注
 *
 * @author rh
 * @email rh@rh.com
 * @date 2024-05-10 23:29:26
 */
public interface MessageService extends IService<MessageEntity> {

    @Async
    void addCommentMessage(ArticleCommentEntity request,Integer ownerId);

    @Async
    void addCommentReplyMessage(CommentReplyEntity commentReplyEntity,Integer ownerId);

    @Async
    void addArticlePraiseMessage(ArticleEntity articleEntity,Integer ownerId);

    @Async
    void addCommenePraiseMessage(ArticleCommentEntity commentEntity,Integer ownerId);

    @Async
    void addReplyPraiseMessage(CommentReplyEntity replyEntity,Integer ownerId);

    Page<MessagePageResponse> getMessagePage(Integer pageIndex, Integer pageSize);

    Long unread();
}

