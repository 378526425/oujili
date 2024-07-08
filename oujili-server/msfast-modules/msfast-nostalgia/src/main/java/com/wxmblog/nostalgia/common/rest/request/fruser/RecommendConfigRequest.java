package com.wxmblog.nostalgia.common.rest.request.fruser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-26 10:50
 **/

@Data
public class RecommendConfigRequest {

    @ApiModelProperty(value = "地址")
    @NotEmpty
    private List<String> city;

    @ApiModelProperty(value = "最小年龄")
    @NotNull
    private Integer minAge;

    @ApiModelProperty(value = "最大年龄")
    @NotNull
    private Integer maxAge;
}
