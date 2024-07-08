package com.wxmblog.nostalgia.common.rest.response.front.article;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wxmblog.base.common.annotation.ForeignTable;
import com.wxmblog.nostalgia.common.enums.article.MessageCategoryEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class MessagePageResponse {

    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 发送者id
     */
    @ApiModelProperty(value = "发送者id")
    private Integer senderId;

    @ApiModelProperty(value = "发送者头像")
    private String senderHeadPortrait;

    @ApiModelProperty(value = "发送者ip")
    private String senderIp;

    @ApiModelProperty(value = "发送者昵称")
    private String senderNickName;

    @ApiModelProperty(value = "发送时间")
    private String createTimeDesc;

    @ApiModelProperty(hidden = true)
    private Date createTime;

    @ApiModelProperty(value = "消息类型 COMMENT(\"评论\")\n" +
            "REPLY(\"回复\")\n" +
            "LIKE_ARTICLE(\"点赞动态\")\n" +
            "LIKE_COMMENT(\"点赞评论\")\n" +
            "LIKE_REPLY(\"点赞回复\")")
    private MessageCategoryEnum category;

    @ApiModelProperty(value = "回复的用户id")
    private Integer replyId;

    @ApiModelProperty(value = "回复用户昵称")
    private String replyNickName;

    @ApiModelProperty(value = "回复或评论内容")
    private String content;

    /**
     * 动态id
     */
    @ApiModelProperty(value = "动态id")
    private Integer articleId;

    @ApiModelProperty(value = "动态作者id")
    private Integer articleUserId;

    @ApiModelProperty(value = "动态作者昵称")
    private String articleUserNickName;

    @ApiModelProperty(value = "动态内容")
    private String articleContent;

    @ApiModelProperty(value = "动态图片")
    private List<String> img;


    @ApiModelProperty(value = "动态评论id")
    private Integer articleCommentId;

    /**
     * 回复评论id
     */
    @ApiModelProperty(value = "评论回复id")
    private Integer commentReplyId;
}
