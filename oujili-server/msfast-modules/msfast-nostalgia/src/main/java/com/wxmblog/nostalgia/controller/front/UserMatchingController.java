package com.wxmblog.nostalgia.controller.front;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.wxmblog.base.common.constant.ParamTypeConstants;
import com.wxmblog.base.common.utils.PageResult;
import com.wxmblog.base.common.web.domain.R;
import com.wxmblog.nostalgia.common.rest.request.fruser.ChoiceRequest;
import com.wxmblog.nostalgia.common.rest.response.front.matching.LikeMePageResponse;
import com.wxmblog.nostalgia.common.rest.response.front.matching.LikePageResponse;
import com.wxmblog.nostalgia.common.rest.response.front.matching.MatchingResponse;
import com.wxmblog.nostalgia.common.rest.response.front.matching.SuccessPageResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wxmblog.nostalgia.service.UserMatchingService;


/**
 * 用户匹配
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
@RestController
@RequestMapping("nostalgia/usermatching")
@Api(tags = "前台-匹配")
public class UserMatchingController {
    @Autowired
    private UserMatchingService userMatchingService;


    @ApiOperation("用户匹配")
    @ApiOperationSort(value = 1)
    @PostMapping("/match")
    public R<MatchingResponse> match(@RequestBody ChoiceRequest request) {
        return R.ok(userMatchingService.match(request));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "数量", defaultValue = "10")
    })
    @ApiOperation("个人中心-喜欢我的")
    @ApiOperationSort(value = 2)
    @GetMapping("/likeMe/page")
    public R<PageResult<LikeMePageResponse>> likeMePage(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return R.ok(userMatchingService.likeMePage(pageIndex, pageSize));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "数量", defaultValue = "10")
    })
    @ApiOperation("个人中心-相互喜欢")
    @ApiOperationSort(value = 3)
    @GetMapping("/success/page")
    public R<PageResult<SuccessPageResponse>> successPage(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
                                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return R.ok(userMatchingService.successPage(pageIndex, pageSize));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "数量", defaultValue = "10")
    })
    @ApiOperation("个人中心-我喜欢")
    @ApiOperationSort(value = 4)
    @GetMapping("/like/page")
    public R<PageResult<LikePageResponse>> likePage(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
                                                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return R.ok(userMatchingService.likePage(pageIndex, pageSize));
    }

    @ApiOperation("用户取消匹配")
    @ApiOperationSort(value = 5)
    @GetMapping("/cancel/match/{id}")
    public R<Void> cancelMatch(@PathVariable Integer id) {
        userMatchingService.cancelMatch(id);
        return R.ok();
    }

}
