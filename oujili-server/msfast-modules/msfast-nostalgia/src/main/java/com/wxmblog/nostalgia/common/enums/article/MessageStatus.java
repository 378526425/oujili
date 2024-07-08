package com.wxmblog.nostalgia.common.enums.article;

public enum MessageStatus {

    UNREAD("未读"),
    READ("已读");

    private String desc;

    MessageStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
