package com.wxmblog.nostalgia.common.enums.article;

public enum PraiseTypeEnum {

    ARTICLE("动态"),
    COMMENT("评论"),
    REPLY("回复");
    private String desc;

    PraiseTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
