package com.wxmblog.nostalgia.common.rest.response.front.payment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayMoneyResponse {

    @ApiModelProperty(value = "价格")
    private Integer price;

    @ApiModelProperty(value = "数量")
    private Integer amount;
}
