package com.wxmblog.nostalgia.controller.front;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.wxmblog.nostalgia.service.PayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 支付订单
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2023-05-04 15:34:57
 */
@RestController
@RequestMapping("nostalgia/payorder")
public class PayOrderController {
    @Autowired
    private PayOrderService payOrderService;

}
