package com.wxmblog.nostalgia.common.rest.request.article;

import com.wxmblog.base.file.annotation.FileSave;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ArticleBackgroundRequest {

    @ApiModelProperty(value = "旧的背景图片")
    @NotBlank
    private String oldBackgroundUrl;

    @ApiModelProperty(value = "新的背景图片")
    @NotBlank
    @FileSave
    private String newBackgroundUrl;
}
