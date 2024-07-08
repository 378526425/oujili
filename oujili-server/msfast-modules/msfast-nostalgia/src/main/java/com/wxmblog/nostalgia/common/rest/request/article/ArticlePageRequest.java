package com.wxmblog.nostalgia.common.rest.request.article;

import com.wxmblog.nostalgia.common.enums.article.ArticleTabEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ArticlePageRequest {

    @ApiModelProperty(value = "tab类型 FOLLOW(\"关注\"),\n" +
            "    RECOMMEND(\"推荐\"),\n" +
            "    CITY(\"同城\")")
    private ArticleTabEnum tabType;

    @ApiModelProperty(hidden = true)
    private Integer userId;

    @ApiModelProperty(hidden = true)
    private List<String> myCity;
}
