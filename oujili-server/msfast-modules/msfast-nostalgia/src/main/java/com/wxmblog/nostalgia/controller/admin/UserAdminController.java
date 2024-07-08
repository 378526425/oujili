package com.wxmblog.nostalgia.controller.admin;

import com.wxmblog.base.auth.common.annotation.AdminRequest;
import com.wxmblog.base.common.constant.ParamTypeConstants;
import com.wxmblog.base.common.utils.PageResult;
import com.wxmblog.base.common.web.domain.R;
import com.wxmblog.nostalgia.common.rest.request.admin.user.UserAdminInfoAddRequest;
import com.wxmblog.nostalgia.common.rest.request.admin.user.UserExamineRequest;
import com.wxmblog.nostalgia.common.rest.request.admin.user.UserInfoRequest;
import com.wxmblog.nostalgia.common.rest.request.admin.user.UserPageRequest;
import com.wxmblog.nostalgia.common.rest.response.admin.user.*;
import com.wxmblog.nostalgia.service.FrUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-06 16:41
 **/
@RestController
@RequestMapping("admin/user")
@Api(tags = "后台-用户管理")
@AdminRequest
public class UserAdminController {

    @Autowired
    FrUserService frUserService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "数量", defaultValue = "10")
    })
    @ApiOperation("用户资料审核列表")
    @ApiOperationSort(value = 1)
    @GetMapping("/examine/page")
    public R<PageResult<UserPageResponse>> examinePage(
            UserPageRequest request,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return R.ok(frUserService.examinePage(request, pageIndex, pageSize));
    }

    @ApiOperation("用户资料审核")
    @ApiOperationSort(value = 2)
    @PutMapping("/examine")
    public R<Void> examine(@RequestBody UserExamineRequest request) {

        frUserService.examine(request);
        return R.ok();
    }

    @ApiOperation("用户资料审核详情")
    @ApiOperationSort(value = 3)
    @GetMapping("/info/{id}")
    public R<UserExamineInfoResponse> examineInfo(@PathVariable Integer id) {
        return R.ok(frUserService.getExamineInfo(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "数量", defaultValue = "10")
    })
    @ApiOperation("用户身份审核列表")
    @ApiOperationSort(value = 4)
    @GetMapping("/identity/page")
    public R<PageResult<UserIdentityPageResponse>> identityPage(
            UserPageRequest request,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return R.ok(frUserService.identityPage(request, pageIndex, pageSize));
    }

    @ApiOperation("用户身份审核详情")
    @ApiOperationSort(value = 5)
    @GetMapping("/identityExamine/{id}")
    public R<IdentityExamineInfoResponse> identityExamine(@PathVariable Integer id) {
        return R.ok(frUserService.identityExamine(id));
    }

    @ApiOperation("用户身份审核")
    @ApiOperationSort(value = 6)
    @PutMapping("/identity/examine")
    public R<Void> identityExamine(@RequestBody UserExamineRequest request) {
        frUserService.identityExamine(request);
        return R.ok();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "数量", defaultValue = "10")
    })
    @ApiOperation("用户学历审核列表")
    @ApiOperationSort(value = 7)
    @GetMapping("/education/page")
    public R<PageResult<UserEducationPageResponse>> userEducationPage(
            UserPageRequest request,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return R.ok(frUserService.userEducationPage(request, pageIndex, pageSize));
    }


    @ApiOperation("用户学历审核详情")
    @ApiOperationSort(value = 8)
    @GetMapping("/educationExamine/{id}")
    public R<EducationExamineInfoResponse> educationExamine(@PathVariable Integer id) {
        return R.ok(frUserService.educationExamine(id));
    }

    @ApiOperation("用户学历审核")
    @ApiOperationSort(value = 9)
    @PutMapping("/education/examine")
    public R<Void> educationExamine(@RequestBody UserExamineRequest request) {
        frUserService.educationExamine(request);
        return R.ok();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "数量", defaultValue = "10")
    })
    @ApiOperation("用户列表")
    @ApiOperationSort(value = 10)
    @GetMapping("/user/page")
    public R<PageResult<UserInfoPageResponse>> userPage(
            UserInfoRequest request,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return R.ok(frUserService.userPage(request, pageIndex, pageSize));
    }

    @ApiOperation("用户详情")
    @ApiOperationSort(value = 11)
    @GetMapping("/userInfo/{id}")
    public R<UserAdminInfoResponse> userAdminInfo(@PathVariable Integer id) {
        return R.ok(frUserService.userAdminInfo(id));
    }

    @ApiOperation("添加或编辑用户")
    @ApiOperationSort(value = 12)
    @PostMapping("/update")
    public R<Void> updateUser(@RequestBody UserAdminInfoAddRequest request) {

        frUserService.updateUser(request);
        return R.ok();
    }

    @ApiOperation("删除用户")
    @ApiOperationSort(value = 13)
    @DeleteMapping("/delete/user/{id}")
    public R<Void> deleteUser(@PathVariable Integer id) {

        frUserService.deleteUser(id);
        return R.ok();
    }
}
