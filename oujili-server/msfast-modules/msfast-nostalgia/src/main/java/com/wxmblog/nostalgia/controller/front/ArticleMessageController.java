package com.wxmblog.nostalgia.controller.front;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.wxmblog.base.common.annotation.AuthIgnore;
import com.wxmblog.base.common.constant.ParamTypeConstants;
import com.wxmblog.base.common.utils.PageResult;
import com.wxmblog.base.common.web.domain.R;
import com.wxmblog.nostalgia.common.rest.response.front.article.MessagePageResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wxmblog.nostalgia.service.MessageService;


/**
 * 备注
 *
 * @author rh
 * @email rh@rh.com
 * @date 2024-05-10 23:29:26
 */
@RestController
@RequestMapping("nostalgia/articleMessage")
@Api(tags = "前台-消息")
public class ArticleMessageController {

    @Autowired
    private MessageService messageService;


    @ApiImplicitParams({@ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页码", defaultValue = "1"), @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "数量", defaultValue = "10")})
    @ApiOperation("消息列表")
    @GetMapping("/page")
    public R<PageResult<MessagePageResponse>> messagePage(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex, @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return R.ok(new PageResult<>(messageService.getMessagePage(pageIndex, pageSize)));
    }

    @ApiOperation("未读消息数")
    @GetMapping("/unread")
    public R<Long> unread() {
        return R.ok(messageService.unread());
    }
}
