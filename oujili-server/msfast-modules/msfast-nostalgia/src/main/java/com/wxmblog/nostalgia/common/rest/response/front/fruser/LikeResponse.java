package com.wxmblog.nostalgia.common.rest.response.front.fruser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-27 15:43
 **/

@Data
public class LikeResponse {

    @ApiModelProperty(value = "我喜欢")
    private Long iLike;

    @ApiModelProperty(value = "喜欢我")
    private Long likeMe;

    @ApiModelProperty(value = "相互喜欢")
    private Long lovers;
}
