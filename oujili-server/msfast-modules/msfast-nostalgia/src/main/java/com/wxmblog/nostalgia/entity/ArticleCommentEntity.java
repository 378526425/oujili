package com.wxmblog.nostalgia.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wxmblog.base.common.entity.BaseEntity;
import lombok.Data;


/**
 * 动态评论
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2024-01-31 15:23:10
 */
@Data
@TableName(value = "article_comment", autoResultMap = true)
public class ArticleCommentEntity extends BaseEntity {


    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 动态id
     */
    @TableField("article_id")
    private Integer articleId;
    /**
     * 评论内容
     */
    @TableField("content")
    private String content;
    /**
     * 点赞数
     */
    @TableField("like_count")
    private Integer likeCount;

    /**
     * 最近的一条回复信息
     */
    @TableField("last_comment_reply_id")
    private Integer lastCommentReplyId;

    @TableField("reply_count")
    private Integer replyCount;
}
