package com.wxmblog.nostalgia.common.rest.response.front.article;

import com.wxmblog.base.common.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ArticleUserInfoResponse {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "头像")
    private String headPortrait;

    @ApiModelProperty(value = "性别 MALE(\"男\"),\n" + "    FEMALE(\"女\")")
    private GenderEnum gender;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "简介资料信息")
    private String information;

    @ApiModelProperty(value = "背景图片")
    private String background;

    @ApiModelProperty(value = "发布数")
    private String push;

    @ApiModelProperty(value = "或赞数")
    private String like;

}
