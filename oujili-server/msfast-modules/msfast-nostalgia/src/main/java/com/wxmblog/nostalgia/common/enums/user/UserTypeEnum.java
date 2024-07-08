package com.wxmblog.nostalgia.common.enums.user;

public enum UserTypeEnum {

    Dummy("虚拟"),
    Normal("正常");

    private String desc;

    UserTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
