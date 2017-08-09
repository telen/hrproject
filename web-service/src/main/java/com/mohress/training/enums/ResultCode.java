package com.mohress.training.enums;

/**
 * 自定义结果码
 *
 * Created by youtao.wan on 2017/8/9.
 */
public enum ResultCode {
    UNKNOWN_EXCEPTION(90000, "服务器内部错误");

    ResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;

    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
