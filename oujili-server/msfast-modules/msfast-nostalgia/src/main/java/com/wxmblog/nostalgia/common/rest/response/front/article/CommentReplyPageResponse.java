package com.wxmblog.nostalgia.common.rest.response.front.article;

import com.wxmblog.base.common.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CommentReplyPageResponse {

    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id 回复人id")
    private Integer userId;

    @ApiModelProperty(value = "头像 回复人头像")
    private String headPortrait;

    @ApiModelProperty(value = "性别 MALE(\"男\"),\n" + "    FEMALE(\"女\")")
    private GenderEnum gender;

    @ApiModelProperty(value = "昵称 回复人名字")
    private String nickName;


    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容 回复内容")
    private String content;

    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数 回复人点赞数")
    private Integer likeCount;

    @ApiModelProperty(value = "是否点赞过 回复人是否点赞")
    private Boolean isLike;

    @ApiModelProperty(value = "发布时间 回复时间")
    private String time;

    @ApiModelProperty(value = "剩余回复数")
    private Integer residueReply;

    @ApiModelProperty(value = "回复对象id 被回复人id")
    private Integer replyUserId;

    @ApiModelProperty(value = "回复对象昵称 被回复人名字")
    private String replyUserName;

    @ApiModelProperty(value = "是否可以删除 是否是自己的评论")
    private Boolean deleteBtn;

    @ApiModelProperty(hidden = true)
    private Date createTime;
}
