package com.wxmblog.nostalgia.common.enums.user;

public enum UserAuthStatusEnum {

    IdentityAuth("实名认证"),
    EducationAuth("学历认证"),
    DOUBLE("双重认证")
    ;

    private String desc;

    UserAuthStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
