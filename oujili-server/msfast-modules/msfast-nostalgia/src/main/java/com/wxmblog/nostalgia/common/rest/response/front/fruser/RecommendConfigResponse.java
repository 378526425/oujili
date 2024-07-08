package com.wxmblog.nostalgia.common.rest.response.front.fruser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-26 10:15
 **/

@Data
public class RecommendConfigResponse {

    @ApiModelProperty(value = "地址")
    private List<String> city;

    @ApiModelProperty(value = "最小年龄")
    private Integer minAge;

    @ApiModelProperty(value = "最大年龄")
    private Integer maxAge;

}
