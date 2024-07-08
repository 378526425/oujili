package com.wxmblog.nostalgia.common.rest.response.front.payment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PayMenuResponse {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "头像")
    private String headPortrait;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "金币余额")
    private Integer goldBalance;

    @ApiModelProperty(value = "支付集合")
    private List<PayMoneyResponse> payMoneyList;
}
