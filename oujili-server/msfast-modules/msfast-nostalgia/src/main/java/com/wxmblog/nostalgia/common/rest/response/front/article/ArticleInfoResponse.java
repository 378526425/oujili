package com.wxmblog.nostalgia.common.rest.response.front.article;

import com.wxmblog.base.common.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleInfoResponse {

    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "头像")
    private String headPortrait;

    @ApiModelProperty(value = "性别 MALE(\"男\"),\n" +
            "    FEMALE(\"女\")")
    private GenderEnum gender;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "简介资料信息")
    private String information;

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

    @ApiModelProperty(hidden = true)
    private Date createTime;
}
