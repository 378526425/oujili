package com.wxmblog.nostalgia.controller.front;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.wxmblog.base.common.annotation.AuthIgnore;
import com.wxmblog.base.common.web.domain.R;
import com.wxmblog.nostalgia.common.rest.response.front.profession.ProfessionResponse;
import com.wxmblog.nostalgia.common.rest.response.front.university.UniversityListResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wxmblog.nostalgia.service.UniversityService;

import java.util.List;


/**
 * 大学
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-20 15:49:55
 */
@RestController
@RequestMapping("nostalgia/university")
@Api(tags = "前台-学校")
public class UniversityController {
    @Autowired
    private UniversityService universityService;

    @ApiOperation("学校下拉列表搜索")
    @ApiOperationSort(value = 1)
    @GetMapping("/nameSelect")
    @AuthIgnore
    public R<List<UniversityListResponse>> nameSelect(@RequestParam String name) {
        return R.ok(universityService.nameSelect(name));
    }

    @ApiOperation("行业下拉列表")
    @ApiOperationSort(value = 2)
    @GetMapping("/professionSelect")
    @AuthIgnore
    public R<List<ProfessionResponse>> professionSelect() {
        return R.ok(universityService.professionSelect());
    }
}
