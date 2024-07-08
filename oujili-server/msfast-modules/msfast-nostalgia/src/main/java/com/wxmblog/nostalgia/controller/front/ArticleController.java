package com.wxmblog.nostalgia.controller.front;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.wxmblog.base.common.annotation.AuthIgnore;
import com.wxmblog.base.common.constant.ParamTypeConstants;
import com.wxmblog.base.common.utils.PageResult;
import com.wxmblog.base.common.web.domain.R;
import com.wxmblog.nostalgia.common.rest.request.article.*;
import com.wxmblog.nostalgia.common.rest.response.front.article.*;
import com.wxmblog.nostalgia.service.ArticleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 用户动态
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2024-01-31 15:23:10
 */
@RestController
@RequestMapping("nostalgia/article")
@Api(tags = "前台-朋友圈")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ApiOperation("发布朋友圈")
    @ApiOperationSort(value = 1)
    @PostMapping("/add")
    public R<Void> addArticle(@RequestBody ArticleAddRequest request) {

        articleService.addArticle(request);
        return R.ok();
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页码", defaultValue = "1"), @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "数量", defaultValue = "10")})
    @ApiOperation("朋友圈列表")
    @ApiOperationSort(value = 2)
    @GetMapping("/page")
    @AuthIgnore
    public R<PageResult<ArticlePageResponse>> articlePage(ArticlePageRequest request, @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex, @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return R.ok(articleService.articlePage(request, pageIndex, pageSize));
    }


    @ApiOperation("点赞")
    @ApiOperationSort(value = 8)
    @PutMapping("/praise")
    public R<Long> praise(@RequestBody PraiseRequest request) {
        return R.ok(articleService.praise(request));
    }

    @ApiOperation("取消点赞")
    @ApiOperationSort(value = 9)
    @PutMapping("/cancel/praise")
    public R<Long> cancelPraise(@RequestBody PraiseRequest request) {

        return R.ok(articleService.cancelPraise(request));
    }


    @ApiOperation("删除动态")
    @ApiOperationSort(value = 12)
    @DeleteMapping("/article/{id}")
    public R<Void> deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
        return R.ok();
    }

}
