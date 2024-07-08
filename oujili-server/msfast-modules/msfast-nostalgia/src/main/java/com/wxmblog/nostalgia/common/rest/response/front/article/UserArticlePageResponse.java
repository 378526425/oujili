package com.wxmblog.nostalgia.common.rest.response.front.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserArticlePageResponse {

    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private List<String> img;

    /**
     * 评论数
     */
    @ApiModelProperty(value = "评论数")
    private Integer commentCount;

    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数")
    private Integer likeCount;

    @ApiModelProperty(value = "是否点赞过")
    private Boolean isLike;

    @ApiModelProperty(value = "发布时间")
    private String time;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "是否可以删除")
    private Boolean deleteBtn;
}
