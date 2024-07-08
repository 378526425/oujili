package com.wxmblog.nostalgia.controller.front;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wxmblog.nostalgia.service.ArticleBackgroundService;


/**
 * 备注
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2024-02-06 17:02:42
 */
@RestController
@RequestMapping("nostalgia/articlebackground")
public class ArticleBackgroundController {
    @Autowired
    private ArticleBackgroundService articleBackgroundService;

}
