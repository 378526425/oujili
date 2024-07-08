package com.wxmblog.nostalgia.controller.front;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wxmblog.nostalgia.service.FrUserExamineService;


/**
 * 用户认证审核
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-28 21:20:43
 */
@RestController
@RequestMapping("nostalgia/fruserexamine")
public class FrUserExamineController {
    @Autowired
    private FrUserExamineService frUserExamineService;

}
