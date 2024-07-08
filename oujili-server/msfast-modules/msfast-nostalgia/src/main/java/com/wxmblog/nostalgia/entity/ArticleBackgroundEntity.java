package com.wxmblog.nostalgia.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wxmblog.base.common.entity.BaseEntity;
import lombok.Data;


/**
 * 备注
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2024-02-06 17:02:42
 */
@Data
@TableName(value = "article_background", autoResultMap = true)
public class ArticleBackgroundEntity extends BaseEntity {


    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 背景图片
     */
    @TableField("background_url")
    private String backgroundUrl;

}
