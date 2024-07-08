package com.wxmblog.nostalgia.common.rest.response.front.article;

import com.wxmblog.base.common.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleCommentPageResponse {

    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "头像 用户头像")
    private String headPortrait;

    @ApiModelProperty(value = "性别 MALE(\"男\"),\n" + "    FEMALE(\"女\")")
    private GenderEnum gender;

    @ApiModelProperty(value = "昵称 用户名")
    private String nickName;

    @ApiModelProperty(value = "简介资料信息")
    private String information;

    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容 用户评论内容")
    private String content;


    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数")
    private Integer likeCount;

    @ApiModelProperty(value = "是否点赞过 用户是否点赞")
    private Boolean isLike;

    @ApiModelProperty(value = "发布时间 用户评论时间")
    private String time;

    @ApiModelProperty(value = "最近一条回复信息")
    private CommentReplyPageResponse lastCommentReply;

    @ApiModelProperty(value = "是否可以删除 是否是自己评论")
    private Boolean deleteBtn;

    @ApiModelProperty(hidden = true)
    private Date createTime;

    //是否显示加载中

    //评论总数
    @ApiModelProperty(value = "评论总数")
    private Integer replyCount;
}
