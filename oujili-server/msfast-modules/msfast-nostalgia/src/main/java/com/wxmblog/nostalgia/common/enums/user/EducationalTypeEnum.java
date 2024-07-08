package com.wxmblog.nostalgia.common.enums.user;

public enum EducationalTypeEnum {

    FullTime("全日制"),
    NOFullTime("非全日制");
    private String desc;

    EducationalTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
