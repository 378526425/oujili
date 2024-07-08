package com.wxmblog.nostalgia.common.enums.article;

public enum ArticleTabEnum {

    FOLLOW("关注"),
    RECOMMEND("推荐"),
    CITY("同城");
    private String desc;

    ArticleTabEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
