package com.wxmblog.nostalgia.controller.front;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.wxmblog.base.common.annotation.AuthIgnore;
import com.wxmblog.base.common.web.domain.R;
import com.wxmblog.nostalgia.common.rest.response.front.area.AreaResponse;
import com.wxmblog.nostalgia.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 地区
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-26 13:40:17
 */
@RestController
@RequestMapping("nostalgia/area")
@Api(tags = "地区")
public class AreaController {

    @Autowired
    private AreaService areaService;


    @ApiOperation("查询省级地区")
    @ApiOperationSort(value = 1)
    @GetMapping("/province")
    @AuthIgnore
    public R<List<AreaResponse>> province() {
        return R.ok(areaService.province());
    }

    @ApiOperation("查询下级地区")
    @ApiOperationSort(value = 2)
    @GetMapping("/son")
    @AuthIgnore
    public R<List<AreaResponse>> sonArea(@RequestParam String parentCode) {
        return R.ok(areaService.sonArea(parentCode));
    }
}
