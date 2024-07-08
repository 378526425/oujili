package com.wxmblog.nostalgia.controller.front;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wxmblog.nostalgia.service.RecommendConfigService;


/**
 * 推荐配置
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
@RestController
@RequestMapping("nostalgia/recommendconfig")
@Api(tags = "前台-用户配置")
public class RecommendConfigController {

    @Autowired
    private RecommendConfigService recommendConfigService;

}
