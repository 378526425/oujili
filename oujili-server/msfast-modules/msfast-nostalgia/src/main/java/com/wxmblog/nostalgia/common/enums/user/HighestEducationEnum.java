package com.wxmblog.nostalgia.common.enums.user;

public enum HighestEducationEnum {

    Specialty("专科"),
    Undergraduate("本科"),
    Master("硕士"),
    Doctor("博士");
    private String desc;

    HighestEducationEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
