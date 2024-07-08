package com.wxmblog.nostalgia.common.rest.request.article;

import com.wxmblog.base.common.annotation.ForeignTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentAddRequest {


    @ApiModelProperty(value = "动态id")
    @NotNull
    @ForeignTable(value = "article", message = "动态不存在")
    private Integer articleId;

    @Length(max = 200, message = "内容不能超过200字")
    @NotBlank(message = "评论内容不能为空")
    private String content;

}
