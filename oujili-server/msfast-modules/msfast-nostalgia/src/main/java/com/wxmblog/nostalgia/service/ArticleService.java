package com.wxmblog.nostalgia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.wxmblog.base.common.utils.PageResult;
import com.wxmblog.nostalgia.common.rest.request.article.*;
import com.wxmblog.nostalgia.common.rest.response.front.article.*;
import com.wxmblog.nostalgia.entity.ArticleEntity;
import org.springframework.scheduling.annotation.Async;


/**
 * 用户动态
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2024-01-31 15:23:10
 */
public interface ArticleService extends IService<ArticleEntity> {

    void addArticle(ArticleAddRequest request);

    PageResult<ArticlePageResponse> articlePage(ArticlePageRequest request, Integer pageIndex, Integer pageSize);



    Long praise(PraiseRequest request);

    Long cancelPraise(PraiseRequest request);


    void deleteArticle(Integer id);

    @Async
    void readMessage(Integer ownerId);
}

