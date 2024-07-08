package com.wxmblog.nostalgia.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wxmblog.base.common.entity.BaseEntity;
import lombok.Data;


/**
 * 评论回复
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2024-01-31 15:23:10
 */

@Data
@TableName(value = "comment_reply", autoResultMap = true)
public class CommentReplyEntity extends BaseEntity {


    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 评论id
     */
    @TableField("article_comment_id")
    private Integer articleCommentId;
    /**
     * 回复评论id
     */
    @TableField("comment_reply_id")
    private Integer commentReplyId;

    @TableField("comment_reply_user_id")
    private Integer commentReplyUserId;



    /**
     * 回复内容
     */
    @TableField("content")
    private String content;
    /**
     * 点赞数
     */
    @TableField("like_count")
    private Integer likeCount;


}
