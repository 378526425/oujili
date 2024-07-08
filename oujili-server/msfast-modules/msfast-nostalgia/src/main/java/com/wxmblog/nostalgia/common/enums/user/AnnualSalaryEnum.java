package com.wxmblog.nostalgia.common.enums.user;

public enum AnnualSalaryEnum {
    Less_Ten("小于10w"),
    Ten_Two("10w到20w"),
    Two_Three("20w到30w"),
    Three_Five("30w到50w"),
    Five_Hundred("50w到100w"),
    Greater_Hundred("大于100w"),
    Secrecy("保密");
    private String desc;

    AnnualSalaryEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
