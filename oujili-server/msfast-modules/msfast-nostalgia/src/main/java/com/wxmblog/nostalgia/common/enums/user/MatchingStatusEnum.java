package com.wxmblog.nostalgia.common.enums.user;

public enum MatchingStatusEnum {
    SUCCESS("匹配成功"),
    LIKE_ME("喜欢我的"),
    LIKE("我喜欢的"),
    NOT_LIKE("我不喜欢"),
    NOT_LIKE_ME("对我无感"),
    NULL("未操作");
    private String desc;

    MatchingStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
