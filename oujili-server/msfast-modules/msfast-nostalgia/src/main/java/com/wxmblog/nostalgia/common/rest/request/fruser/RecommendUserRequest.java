package com.wxmblog.nostalgia.common.rest.request.fruser;

import com.wxmblog.base.common.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-08 16:18
 **/

@Data
public class RecommendUserRequest {

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别")
    private GenderEnum gender;

}
