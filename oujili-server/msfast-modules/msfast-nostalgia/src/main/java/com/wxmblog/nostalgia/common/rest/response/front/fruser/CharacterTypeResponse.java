package com.wxmblog.nostalgia.common.rest.response.front.fruser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-08 17:44
 **/

@Data
public class CharacterTypeResponse {

    @ApiModelProperty(value = "性格类型名称")
    private String name;

    @ApiModelProperty(value = "外向分数")
    private CharacterResponse extroversionScore;

    @ApiModelProperty(value = "想像分数")
    private CharacterResponse imagineScore;

    @ApiModelProperty(value = "感性分数")
    private CharacterResponse perceptualScore;

    @ApiModelProperty(value = "计划分数")
    private CharacterResponse planScore;
}
