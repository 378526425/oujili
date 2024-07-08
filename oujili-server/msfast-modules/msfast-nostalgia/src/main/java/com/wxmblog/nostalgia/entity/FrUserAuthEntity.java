package com.wxmblog.nostalgia.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wxmblog.base.common.entity.BaseEntity;
import com.wxmblog.nostalgia.common.enums.user.AuthStatusEnum;
import com.wxmblog.nostalgia.common.enums.user.AuthTypeEnum;
import com.wxmblog.nostalgia.common.handle.UserAuthImgListHandler;
import lombok.Data;

import java.util.List;


/**
 * 用户认证
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2023-01-03 16:57:02
 */
@Data
@TableName(value = "fr_user_auth", autoResultMap = true)
public class FrUserAuthEntity extends BaseEntity {

    /**
     * 审核状态
     */
    @TableField("auth_status")
    private AuthStatusEnum authStatus;
    /**
     * 认证类型
     */
    @TableField("auth_type")
    private AuthTypeEnum authType;
    /**
     * 材料
     */
    @TableField(value = "img_list", typeHandler = UserAuthImgListHandler.class)
    private List<String> imgList;

    @TableField("user_id")
    private Integer userId;

}
