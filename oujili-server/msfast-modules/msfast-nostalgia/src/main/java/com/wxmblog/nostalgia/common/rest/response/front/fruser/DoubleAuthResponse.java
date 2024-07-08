package com.wxmblog.nostalgia.common.rest.response.front.fruser;

import com.wxmblog.nostalgia.common.enums.user.AuthStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-03 16:27
 **/

@Data
public class DoubleAuthResponse {

    @ApiModelProperty(value = "身份认证  EXAMINE(\"审核中\"),\n" +
            "    PASS(\"通过\"),\n" +
            "    REFUSE(\"拒绝\")")
    private AuthStatusEnum identityAuth;

    @ApiModelProperty(value = "学历认证  EXAMINE(\"审核中\"),\n" +
            "    PASS(\"通过\"),\n" +
            "    REFUSE(\"拒绝\")")
    private AuthStatusEnum educationAuth;
}
