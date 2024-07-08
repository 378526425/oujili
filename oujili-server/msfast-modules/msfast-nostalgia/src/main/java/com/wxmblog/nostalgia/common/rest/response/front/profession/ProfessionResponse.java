package com.wxmblog.nostalgia.common.rest.response.front.profession;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProfessionResponse {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;
}
