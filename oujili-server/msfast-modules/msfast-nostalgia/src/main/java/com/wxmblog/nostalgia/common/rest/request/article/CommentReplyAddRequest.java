package com.wxmblog.nostalgia.common.rest.request.article;

import com.wxmblog.base.common.annotation.ForeignTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentReplyAddRequest {


    @ApiModelProperty(value = "评论id")
    @NotNull
    @ForeignTable(value = "article_comment", message = "评论不存在")
    private Integer articleCommentId;

    /**
     * 回复评论id
     */
    @ForeignTable(value = "comment_reply", message = "回复信息不存在")
    private Integer commentReplyId;

    @Length(max = 200, message = "内容不能超过200字")
    @NotBlank(message = "回复内容不能为空")
    private String content;

}
