package com.wxmblog.nostalgia.common.rest.response.front.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AuthResponse {

    @ApiModelProperty(value = "实名认证材料")
    private List<String> identityImgList;

    @ApiModelProperty(value = "学历认证")
    private List<String> EducationImgList;
}
