package com.wxmblog.nostalgia.common.rest.request.article;

import lombok.Data;

@Data
public class ArticleCommentPageRequest {

    private Integer userId;

    private Integer articleId;
}
