package com.wxmblog.nostalgia.common.enums.user;

public enum CharacterTypeEnum {

    extroversion("外向"),
    imagine("想像"),
    perceptual("感性"),
    plan("计划");
    private String desc;

    CharacterTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
