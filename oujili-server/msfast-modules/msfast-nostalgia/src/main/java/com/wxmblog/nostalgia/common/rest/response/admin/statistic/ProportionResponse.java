package com.wxmblog.nostalgia.common.rest.response.admin.statistic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-03-08 17:55
 **/

@Data
public class ProportionResponse {

    @ApiModelProperty(value = "数量")
    private Long value;

    @ApiModelProperty(value = "名称")
    private String name;
}
