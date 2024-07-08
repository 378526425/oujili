package com.wxmblog.nostalgia.common.enums.user;

public enum LoveGoalEnum {

    One_Year("一年内"),
    Two_Year("两年内"),
    Any_Time("随时"),
    Un_known("暂不考虑");

    private String desc;

    LoveGoalEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
