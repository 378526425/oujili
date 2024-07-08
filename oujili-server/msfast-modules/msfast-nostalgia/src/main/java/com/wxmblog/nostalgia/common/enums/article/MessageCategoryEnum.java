package com.wxmblog.nostalgia.common.enums.article;

public enum MessageCategoryEnum {

    COMMENT("评论"),
    REPLY("回复"),
    LIKE_ARTICLE("点赞动态"),
    LIKE_COMMENT("点赞评论"),
    LIKE_REPLY("点赞回复")
    ;
    private String desc;

    MessageCategoryEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
