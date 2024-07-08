package com.wxmblog.nostalgia.common.rest.response.admin.statistic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-03-06 15:38
 **/

@Data
public class OutlineResponse {

    @ApiModelProperty(value = "用户总数")
    private Long userCount;

    @ApiModelProperty(value = "今日注册用户")
    private Long todayRegister;

    @ApiModelProperty(value = "24小时内注册用户")
    private Long register24;

    @ApiModelProperty(value = "昨日注册用户")
    private Long yesterdayRegister;

    @ApiModelProperty(value = "今日在线用户数")
    private Long todayOnline;

    @ApiModelProperty(value = "24小时内在线用户数")
    private Long online24;

    @ApiModelProperty(value = "性别饼图")
    private List<ProportionResponse> genderPie;

    @ApiModelProperty(value = "城市柱状图")
    private List<ProportionResponse> cityBar;

}
