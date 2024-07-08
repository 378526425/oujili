package com.wxmblog.nostalgia.controller.front;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.wxmblog.base.common.annotation.AuthIgnore;
import com.wxmblog.base.common.web.domain.R;
import com.wxmblog.nostalgia.common.rest.request.fruser.*;
import com.wxmblog.nostalgia.common.rest.response.front.fruser.*;
import com.wxmblog.nostalgia.service.FrUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 前台用户
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
@RestController
@RequestMapping("nostalgia/fruser")
@Api(tags = "前台-用户")
public class FrUserController {

    @Autowired
    private FrUserService frUserService;


    @ApiOperation("推荐用户信息")
    @ApiOperationSort(value = 1)
    @GetMapping("/recommendUserInfo")
    @AuthIgnore
    public R<RecommendUserInfoResponse> recommendUserInfo(RecommendUserRequest request) {
        return R.ok(frUserService.getRecommendUserInfo(request));
    }

    @ApiOperation("查看用户配置")
    @ApiOperationSort(value = 2)
    @GetMapping("/user/configInfo")
    public R<RecommendConfigResponse> userConfigInfo() {
        return R.ok(frUserService.getRecommendConfig());
    }

    @ApiOperation("修改用户配置")
    @ApiOperationSort(value = 3)
    @PutMapping("/update/configInfo")
    public R<Void> updateConfigInfo(@RequestBody RecommendConfigRequest request) {
        frUserService.updateConfigInfo(request);
        return R.ok();
    }

    @ApiOperation("我的-个人中心")
    @ApiOperationSort(value = 4)
    @GetMapping("/personalCenter")
    public R<PersonalCenterResponse> personalCenter() {
        return R.ok(frUserService.getPersonalCenter());
    }

    @ApiOperation("我的-个人中心-资料编辑详情")
    @ApiOperationSort(value = 5)
    @GetMapping("/personalInfo")
    public R<PersonalInfoResponse> personalInfo() {
        return R.ok(frUserService.personalInfo());
    }

    @ApiOperation("我的-个人中心-资料编辑-编辑相册")
    @ApiOperationSort(value = 6)
    @PutMapping("/photoEdit")
    public R<Void> photoEdit(@RequestBody PhotoEditRequest request) {
        frUserService.photoEdit(request);
        return R.ok();
    }

    @ApiOperation("我的-个人中心-资料编辑-基础资料编辑详情")
    @ApiOperationSort(value = 7)
    @GetMapping("/baseInfo")
    public R<BaseInfoResponse> baseInfo() {
        return R.ok(frUserService.baseInfo());
    }

    @ApiOperation("个人中心-基础资料编辑")
    @ApiOperationSort(value = 8)
    @PutMapping("/baseInfo/edit")
    public R<Void> baseInfoEdit(@RequestBody BaseInfoEditRequest request) {
        frUserService.baseInfoEdit(request);
        return R.ok();
    }

    @ApiOperation("个人中心-性格编辑")
    @ApiOperationSort(value = 9)
    @PutMapping("/character/edit")
    public R<Void> characterEdit(@RequestBody CharacterRequest request) {
        frUserService.characterEdit(request);
        return R.ok();
    }

    @ApiOperation("我的-个人中心-双重认证")
    @ApiOperationSort(value = 10)
    @GetMapping("/doubleAuth")
    public R<DoubleAuthResponse> doubleAuth() {
        return R.ok(frUserService.doubleAuth());
    }

    @ApiOperation("用户详情")
    @ApiOperationSort(value = 11)
    @GetMapping("/userInfo/{id}")
    public R<UserInfoResponse> userInfo(@PathVariable Integer id) {
        return R.ok(frUserService.userInfo(id));
    }

    @ApiOperation("前台用户详情")
    @ApiOperationSort(value = 12)
    @GetMapping("/front/userInfo/{id}")
    @AuthIgnore
    public R<FrontUserInfoResponse> frontUserInfo(@PathVariable Integer id) {
        return R.ok(frUserService.frontUserInfo(id));
    }

    @ApiOperation("用户注销")
    @ApiOperationSort(value = 13)
    @GetMapping("/delete/fruser")
    public R<Void> deleteFruser() {
        frUserService.deleteFruser();
        return R.ok();
    }
}
