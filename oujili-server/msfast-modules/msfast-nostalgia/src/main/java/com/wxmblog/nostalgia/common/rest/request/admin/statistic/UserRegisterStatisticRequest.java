package com.wxmblog.nostalgia.common.rest.request.admin.statistic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-03-10 16:51
 **/

@Data
public class UserRegisterStatisticRequest {

    @ApiModelProperty(value = "注册开始时间 yyyy-MM-dd HH:mm:ss")
    private String createTimeStart;

    @ApiModelProperty(value = "注册结束时间 yyyy-MM-dd HH:mm:ss")
    private String createTimeEnd;

}
