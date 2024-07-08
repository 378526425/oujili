package com.wxmblog.nostalgia.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wxmblog.base.common.entity.BaseEntity;
import com.wxmblog.base.common.handler.BaseStringListTypeHandler;
import lombok.Data;

import java.util.List;


/**
 * 用户动态
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2024-01-31 15:23:10
 */
@Data
@TableName(value = "article", autoResultMap = true)
public class ArticleEntity extends BaseEntity {


    /**
     * 内容
     */
    @TableField("content")
    private String content;
    /**
     * 图片
     */
    @TableField(value = "img", typeHandler = BaseStringListTypeHandler.class)
    private List<String> img;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 评论数
     */
    @TableField("comment_count")
    private Integer commentCount;
    /**
     * 点赞数
     */
    @TableField("like_count")
    private Integer likeCount;

}
