package com.wxmblog.nostalgia.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wxmblog.base.common.entity.BaseEntity;
import com.wxmblog.nostalgia.common.enums.user.AuthStatusEnum;
import com.wxmblog.nostalgia.common.enums.user.AuthTypeEnum;
import lombok.Data;


/**
 * 用户认证审核
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-28 21:20:43
 */
@Data
@TableName(value = "fr_user_examine", autoResultMap = true)
public class FrUserExamineEntity extends BaseEntity {


    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 审核状态
     */
    @TableField("auth_status")
    private AuthStatusEnum authStatus;
    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;
    /**
     * 审核类型
     */
    @TableField("auth_type")
    private AuthTypeEnum authType;

}
