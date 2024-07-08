package com.wxmblog.nostalgia.controller.admin;

import com.wxmblog.base.auth.common.annotation.AdminRequest;
import com.wxmblog.base.common.web.domain.R;
import com.wxmblog.nostalgia.common.rest.request.admin.statistic.UserRegisterStatisticRequest;
import com.wxmblog.nostalgia.common.rest.response.admin.statistic.OutlineResponse;
import com.wxmblog.nostalgia.common.rest.response.admin.statistic.ProportionResponse;
import com.wxmblog.nostalgia.service.FrUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-03-06 15:01
 **/

@RestController
@RequestMapping("admin/statistic")
@Api(tags = "后台-用户统计")
@AdminRequest
public class UserStatisticController {

    @Autowired
    FrUserService frUserService;

    @ApiOperation("用户概要统计")
    @ApiOperationSort(value = 1)
    @GetMapping("/outline")
    public R<OutlineResponse> outline() {
        return R.ok(frUserService.outline());
    }

    @ApiOperation("用户注册折线")
    @ApiOperationSort(value = 2)
    @GetMapping("/user/line")
    public R<List<ProportionResponse>> userRegister(UserRegisterStatisticRequest request) {
        return R.ok(frUserService.userRegister(request));
    }
}
