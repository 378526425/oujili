package com.wxmblog.nostalgia.common.rest.response.front.area;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-26 13:49
 **/

@Data
public class AreaResponse {

    /**
     * 区域代码
     */
    @ApiModelProperty(value = "区域代码")
    private String regionCode;

    /**
     * 区域名称
     */
    @ApiModelProperty(value = "区域名称")
    private String regionName;

    /**
     * 父级代码
     */
    @ApiModelProperty(value = "父级代码")
    private String parentCode;

}
