package com.wxmblog.nostalgia.common.rest.response.front.fruser;

import com.wxmblog.nostalgia.common.enums.user.AuthStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-27 13:51
 **/

@Data
public class AdditionalResponse {

    @ApiModelProperty(value = "身份认证")
    private AuthStatusEnum identityAuth;

    @ApiModelProperty(value = "学历认证")
    private AuthStatusEnum educationAuth;

    @ApiModelProperty(value = "资料待审核")
    private AuthStatusEnum waitApprovedStatus;

}
