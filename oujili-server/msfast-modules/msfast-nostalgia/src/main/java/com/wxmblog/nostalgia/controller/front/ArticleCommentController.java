package com.wxmblog.nostalgia.controller.front;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wxmblog.nostalgia.service.ArticleCommentService;


/**
 * 动态评论
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2024-01-31 15:23:10
 */
@RestController
@RequestMapping("nostalgia/articlecomment")
public class ArticleCommentController {
    @Autowired
    private ArticleCommentService articleCommentService;

}
