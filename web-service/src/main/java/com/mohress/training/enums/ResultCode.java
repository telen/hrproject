package com.mohress.training.enums;

/**
 * 自定义结果码
 *
 */
public enum ResultCode {

    SUCCESS("000000", "成功"),

    FAIL("000001", "失败"),

    AUDIT_SUCCESS("500000", "审核成功"),

    AUDIT_FAIL("500001", "审核失败"),

    AUDIT_NO_PRIVILEGE("", "审核无权限"),

    AUDIT_RULE_NO_PASS("", "审核规则校验不通过"),

    UNKNOWN_EXCEPTION("900000", "服务器内部错误");

    ResultCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;

    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
