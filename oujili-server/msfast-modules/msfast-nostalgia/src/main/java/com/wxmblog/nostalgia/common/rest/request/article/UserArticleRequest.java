package com.wxmblog.nostalgia.common.rest.request.article;

import lombok.Data;

@Data
public class UserArticleRequest {

    private Integer userId;

    private Integer ownerId;
}
