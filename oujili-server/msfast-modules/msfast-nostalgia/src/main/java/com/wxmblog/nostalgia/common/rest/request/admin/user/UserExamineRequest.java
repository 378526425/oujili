package com.wxmblog.nostalgia.common.rest.request.admin.user;

import com.wxmblog.nostalgia.common.enums.user.AuthStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-06 16:55
 **/

@Data
public class UserExamineRequest {

    @ApiModelProperty(value = "用户id")
    @NotNull
    private Integer userId;

    @ApiModelProperty(value = "处理结果 PASS(\"通过\"),\n" +
            "    REFUSE(\"拒绝\")")
    @NotNull
    private AuthStatusEnum result;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "版本")
    @NotNull
    private Integer version;

}
