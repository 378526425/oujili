package com.wxmblog.nostalgia.common.rest.request.fruser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChoiceRequest {

    /**
     * 喜欢或是不喜欢
     */
    @ApiModelProperty(value = "喜欢或是不喜欢")
    @NotNull
    private Boolean result;
    /**
     * 被喜欢的用户
     */
    @ApiModelProperty(value = "用户id")
    @NotNull
    private Integer otherUser;
}
