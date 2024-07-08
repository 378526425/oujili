package com.wxmblog.nostalgia.common.rest.request.message;

import com.wxmblog.nostalgia.common.enums.article.MessageCategoryEnum;
import lombok.Data;

import java.util.List;

@Data
public class MessageAddRequest {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 发送者id
     */
    private Integer senderId;
    /**
     * 内容
     */
    private String content;

    /**
     * 消息类型
     */
    private MessageCategoryEnum category;

    /**
     * 回复对象id
     */
    private Integer replyId;

    /**
     * 动态id
     */
    private Integer articleId;

    private Integer articleCommentId;

    private Integer commentReplyId;


    private String articleContent;

    private List<String> articleImg;
}
