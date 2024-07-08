package com.wxmblog.nostalgia.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wxmblog.base.common.entity.BaseEntity;
import com.wxmblog.base.common.handler.BaseStringListTypeHandler;
import com.wxmblog.nostalgia.common.enums.article.MessageCategoryEnum;
import com.wxmblog.nostalgia.common.enums.article.MessageStatus;
import lombok.Data;

import java.util.List;


/**
 * 备注
 *
 * @author rh
 * @email rh@rh.com
 * @date 2024-05-10 23:29:26
 */
@Data
@TableName(value = "message", autoResultMap = true)
public class MessageEntity extends BaseEntity {

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 发送者id
     */
    @TableField("sender_id")
    private Integer senderId;
    /**
     * 内容
     */
    @TableField("content")
    private String content;
    /**
     * 消息类型
     */
    @TableField("category")
    private MessageCategoryEnum category;
    /**
     * 回复对象id
     */
    @TableField("reply_id")
    private Integer replyId;
    /**
     * 动态id
     */
    @TableField("article_id")
    private Integer articleId;

    @TableField("article_comment_id")
    private Integer articleCommentId;

    @TableField("comment_reply_id")
    private Integer commentReplyId;

    @TableField("article_content")
    private String articleContent;

    @TableField(value = "article_img", typeHandler = BaseStringListTypeHandler.class)
    private List<String> articleImg;

    @TableField("status")
    private MessageStatus status;
}
