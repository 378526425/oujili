package com.wxmblog.nostalgia.controller.front;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wxmblog.nostalgia.service.UserToLikeService;


/**
 * 用户点赞关联
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2024-02-04 11:37:30
 */
@RestController
@RequestMapping("nostalgia/usertolike")
public class UserToLikeController {
    @Autowired
    private UserToLikeService userToLikeService;

}
