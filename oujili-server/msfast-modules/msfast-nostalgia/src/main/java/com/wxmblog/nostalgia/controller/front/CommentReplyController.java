package com.wxmblog.nostalgia.controller.front;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wxmblog.nostalgia.service.CommentReplyService;


/**
 * 评论回复
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2024-01-31 15:23:10
 */
@RestController
@RequestMapping("nostalgia/commentreply")
public class CommentReplyController {
    @Autowired
    private CommentReplyService commentReplyService;

}
