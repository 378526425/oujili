package com.wxmblog.nostalgia.common.rest.response.admin.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wxmblog.base.common.enums.FrUserStatusEnum;
import com.wxmblog.base.common.enums.GenderEnum;
import com.wxmblog.nostalgia.common.enums.user.AuthStatusEnum;
import com.wxmblog.nostalgia.common.enums.user.UserTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-02-13 16:16
 **/

@Data
public class UserPageResponse {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 用户状态 启用/停用/注销
     */
    @ApiModelProperty(value = "用户状态 ENABLE(\"启用\"),\n" +
            "    DISABLE(\"禁用\"),\n" +
            "    LOGOFF(\"注销\")")
    private FrUserStatusEnum status;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String headPortrait;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别 MALE(\"男\"),\n" +
            "    FEMALE(\"女\")")
    private GenderEnum gender;


    /**
     * 城市
     */
    @ApiModelProperty(value = "城市")
    private String city;

    /**
     * 用户资料状态
     */
    @ApiModelProperty(value = "用户资料状态 EXAMINE(\"审核中\"),\n" +
            "    PASS(\"通过\"),\n" +
            "    REFUSE(\"拒绝\")")
    private AuthStatusEnum waitApprovedStatus;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型 Dummy(\"虚拟\"),\n" +
            "    Normal(\"正常\")")
    private UserTypeEnum userType;


    @ApiModelProperty(value = "最近活跃时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date latelyTime;

}
