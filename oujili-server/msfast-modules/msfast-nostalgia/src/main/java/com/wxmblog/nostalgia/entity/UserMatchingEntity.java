package com.wxmblog.nostalgia.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wxmblog.base.common.entity.BaseEntity;
import lombok.Data;

/**
 * 用户匹配
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
@Data
@TableName(value = "user_matching", autoResultMap = true)
public class UserMatchingEntity extends BaseEntity {


    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 喜欢或是不喜欢
     */
    @TableField("result")
    private Boolean result;
    /**
     * 被喜欢的用户
     */
    @TableField("other_user")
    private Integer otherUser;

    @TableField("desc_info")
    private String descInfo;

    @TableField("is_unlock")
    private Boolean isUnlock;
}
