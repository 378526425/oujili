package com.wxmblog.nostalgia.dao;

import com.wxmblog.nostalgia.common.rest.request.article.*;
import com.wxmblog.nostalgia.common.rest.response.front.article.*;
import com.wxmblog.nostalgia.entity.ArticleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户动态
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2024-01-31 15:23:10
 */
@Mapper
public interface ArticleDao extends BaseMapper<ArticleEntity> {


    List<ArticlePageResponse> getArticlePage(ArticlePageRequest request);

    ArticleInfoResponse articleInfo(ArticleInfoRequest request);

    List<ArticleCommentPageResponse> getCommentPage(ArticleCommentPageRequest request);

    List<CommentReplyPageResponse> getReplyPage(ReplyRequest request);

    List<UserArticlePageResponse> getUserArticle(UserArticleRequest request);

    List<String> getUserArticleView(Integer userId);

    ArticleCount selectCountSum(Integer userId);

    List<UserArticlePageResponse> getPraiseArticle(UserArticleRequest request);

    List<UserArticlePageResponse> getCommentArticle(UserArticleRequest request);
}
