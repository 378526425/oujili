package com.wxmblog.nostalgia.common.enums.user;

public enum PayOrderStatusEnum {
    PRE_PAY("预支付"),
    SUCCESS("支付成功");
    private String desc;

    PayOrderStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
