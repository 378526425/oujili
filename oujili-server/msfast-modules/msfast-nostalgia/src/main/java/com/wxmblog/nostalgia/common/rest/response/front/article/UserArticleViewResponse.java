package com.wxmblog.nostalgia.common.rest.response.front.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserArticleViewResponse {

    @ApiModelProperty(value = "朋友圈图片")
    private List<String> articleImg;

    @ApiModelProperty(value = "朋友圈文字")
    private String articleContent;
}
