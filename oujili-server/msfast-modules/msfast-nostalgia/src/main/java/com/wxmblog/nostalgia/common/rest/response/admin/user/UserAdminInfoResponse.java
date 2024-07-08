package com.wxmblog.nostalgia.common.rest.response.admin.user;

import com.wxmblog.nostalgia.common.enums.user.AuthStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-03-02 16:09
 **/

@Data
public class UserAdminInfoResponse extends BaseUserInfoResponse {

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private String lon;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private String lat;

    /**
     * 用户资料状态
     */
    @ApiModelProperty(value = "资料认证状态")
    private AuthStatusEnum authStatus;

    @ApiModelProperty(value = "资料相册")
    private List<String> imgList;

    @ApiModelProperty(value = "身份认证状态")
    private AuthStatusEnum identityAuth;

    @ApiModelProperty(value = "学历认证状态")
    private AuthStatusEnum educationAuth;

}
