package com.wxmblog.nostalgia.common.exception;

import com.wxmblog.base.common.interfaces.BaseExceptionEnumInterface;

public enum UserExceptionEnum implements BaseExceptionEnumInterface {

    SEARCH_PARAM_EMPTY_EXCEPTION(12001, "查询条件不可为空"),
    MATCHING_BEYOND_LIMIT_EXCEPTION(12002, "匹配数限额"),
    MIN_AGE_GREATER_EXCEPTION(12003, "最小年龄大于最大年龄"),
    FIRST_PHOTO_NOT_DELETE_EXCEPTION(12004, "头像不能删除"),
    NEW_URL_NOT_EMPTY_EXCEPTION(12005, "新图片地址不可为空"),
    OLD_URL_NOT_EMPTY_EXCEPTION(12006, "旧图片地址不可为空"),
    USER_VERSION_DIFFERENT_EXCEPTION(12007, "用户信息已变更"),
    USER_AUTH_NOT_PASS_EXCEPTION(12008, "用户资料审核暂未通过"),
    USER_NOT_DUMMY_EXCEPTION(12009, "用户不是虚拟用户"),
    BALANCE_LESS(12009, "余额不足"),
    LIKE_ME_LESS_PRICE(12010, "喜欢你的数量不够全部解锁"),

    ARTICLE_IS_EMPTY(12011, "内容不能为空");

    private Integer code;
    private String msg;

    UserExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
