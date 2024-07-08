package com.wxmblog.nostalgia.controller.front;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxmblog.base.common.annotation.AuthIgnore;
import com.wxmblog.base.common.utils.CPUUtils;
import com.wxmblog.base.common.utils.TokenUtils;
import com.wxmblog.base.common.web.domain.R;
import com.wxmblog.base.file.service.MsfFileService;
import com.wxmblog.nostalgia.common.rest.request.fruser.ChoiceRequest;
import com.wxmblog.nostalgia.common.rest.request.fruser.RecommendUserRequest;
import com.wxmblog.nostalgia.common.rest.request.test.ForeignRequest;
import com.wxmblog.nostalgia.common.rest.response.front.fruser.RecommendUserInfoResponse;
import com.wxmblog.nostalgia.entity.UserMatchingEntity;
import com.wxmblog.nostalgia.service.FrUserService;
import com.wxmblog.nostalgia.service.TestService;
import com.wxmblog.nostalgia.service.UserMatchingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-17 10:12
 **/

@RestController
@Api(tags = "测试接口")
@RequestMapping("test")
@Slf4j
public class TestController {

    @Autowired
    UserMatchingService userMatchingService;

    @Autowired
    FrUserService frUserService;

    @Autowired
    TestService testService;

    @Autowired
    MsfFileService msfFileService;

    @ApiOperation("重置匹配结果")
    @ApiOperationSort(value = 1)
    @PostMapping("/reload/matching")
    @AuthIgnore
    public R<Void> reloadMatching() {

        Wrapper<UserMatchingEntity> wrapper = new QueryWrapper<UserMatchingEntity>().lambda().eq(UserMatchingEntity::getUserId, TokenUtils.getOwnerId());
        userMatchingService.remove(wrapper);
        return R.ok();
    }

    @ApiOperation("删除前台用户")
    @ApiOperationSort(value = 1)
    @GetMapping("/delete/fruser/{id}")
    @AuthIgnore
    public R<Void> deleteFruser(@PathVariable Integer id) {
        testService.deleteFruser(id);
        return R.ok();
    }

    @ApiOperation("测试")
    @ApiOperationSort(value = 1)
    @GetMapping("/matching")
    public R<Void> matching() {

        RecommendUserInfoResponse userInfoResponse = frUserService.getRecommendUserInfo(new RecommendUserRequest());
        ChoiceRequest choiceRequest = new ChoiceRequest();
        choiceRequest.setOtherUser(userInfoResponse.getId());
        choiceRequest.setResult(true);
        userMatchingService.match(choiceRequest);

        return R.ok();
    }

    @ApiOperation("外键表")
    @ApiOperationSort(value = 1)
    @PostMapping("/foreign")
    public R<Void> foreign(@RequestBody ForeignRequest request) {

        return R.ok();
    }

    @ApiOperation("提取字符串")
    @ApiOperationSort(value = 1)
    @GetMapping("/tiqu")
    public R tiqu() {

        msfFileService.deleteFileByRichText("11");
        return R.ok();
    }

    @ApiOperation("获取设备标识")
    @ApiOperationSort(value = 1)
    @GetMapping("/number")
    public R<String>number() {
        String localMacAddress = null;
        try {
            localMacAddress = CPUUtils.getCpuId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("localMacAddress:{}", localMacAddress);
        return R.ok(localMacAddress);
    }
}
