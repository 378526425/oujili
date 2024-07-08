package com.wxmblog.nostalgia.common.rest.response.front.matching;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MatchingResponse {

    public MatchingResponse(Boolean result) {
        this.result = result;
    }

    @ApiModelProperty(value = "匹配结果 true就是匹配成功 需要弹出弹窗特效")
    private Boolean result;

    @ApiModelProperty(value = "自己的id")
    private Integer selfId;

    /**
     * 头像
     */
    @ApiModelProperty(value = "自己的头像")
    private String selfHeadPortrait;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "自己的昵称")
    private String selfNickName;

    @ApiModelProperty(value = "对方的id")
    private Integer otherId;

    /**
     * 头像
     */
    @ApiModelProperty(value = "对方的头像")
    private String otherHeadPortrait;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "对方的昵称")
    private String otherNickName;

}
