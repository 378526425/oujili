package com.wxmblog.nostalgia.controller.front;

import com.wxmblog.base.common.web.domain.R;
import com.wxmblog.nostalgia.common.rest.request.payment.SeeLikeMeRequest;
import com.wxmblog.nostalgia.common.rest.response.front.payment.PayMenuResponse;
import com.wxmblog.nostalgia.common.rest.response.front.payment.ViewLikeMeResponse;
import com.wxmblog.nostalgia.service.FrUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("nostalgia/payment")
@Api(tags = "支付")
public class PaymentController {

    @Autowired
    private FrUserService frUserService;

    @ApiOperation("支付选择查询")
    @ApiOperationSort(value = 1)
    @GetMapping("/pay/menu")
    public R<PayMenuResponse> payMenu() {
        return R.ok(frUserService.payMenu());
    }

    @ApiOperation("支付选择查询-通过code")
    @ApiOperationSort(value = 2)
    @GetMapping("/pay/menu/code")
    public R<PayMenuResponse> payMenuCode(@RequestParam String code) {
        return R.ok(frUserService.payMenuByCode(code));
    }


    @ApiOperation("查看解锁价格")
    @ApiOperationSort(value = 3)
    @GetMapping("/viewLikeMePrice")
    public R<ViewLikeMeResponse> viewLikeMePrice() {
        return R.ok(frUserService.viewLikeMePrice());
    }

    @ApiOperation("用户解锁喜欢自己")
    @ApiOperationSort(value = 4)
    @PostMapping("/unlock/likeme")
    public R<Void> unlockLikeme(@RequestBody SeeLikeMeRequest request) {
        frUserService.unlockLikeme(request);
        return R.ok();
    }

}
