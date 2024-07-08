package com.wxmblog.nostalgia.common.enums.user;

public enum CharacterPosition {

    LEFT("左边"),
    RIGHT("右边");
    private String desc;

    CharacterPosition(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
