package com.mohress.training.dto;

/**
 * @author youtao.wan
 * @date 16-9-21
 */
public enum StatusCode {
    //基础响应码
    SUCCESS("000000", "SUCCESS"),
    FAIL("000001", "FAIL"),
    PARAM_INVALID("000002", "INVALID PARAMETER"),
    NO_RESPONSE("000003", "NO RESPONSE"),
    IO_EXCEPTION("000004", "IO EXCEPTION"),
    OVERTIME("000005", "REQUEST TIMED OUT"),
    UNKNOWN_EXCEPTION("000006", "UNKNOWN_EXCEPTION"),
    VALID_STATUS("000007", "VALID_STATUS"),
    USER_NOT_EXIST("000008", "USER_NOT_EXIST"),

    /**
     * 未登陆
     */
    NOT_LOGIN("000009", "NOT_LOGIN"),

    //支付响应码
    PAY_SUCCESS("100000", "PAY SUCCESS"),
    PAY_FAIL("100001", "PAY_FAIL"),

    //订单相关的响应码
    QUERY_SUCCESS("200000", "QUERY SUCCESS"),
    ORDER_NO_EXIST("200001", "ORDER NOT EXIST"),

    // 退款响应码
    REFUND_SUCCESS("300000", "REFUND_SUCCESS"),
    REFUND_FAIL("300001", "REFUND_FAIL"),

    //管理员登录
    AUTH_SUCCESS("400000", "AUTH_SUCCESS"),
    AUTH_FAIL("400001", "AUTH_FAIL"),
    NO_GOODS("110001", "NO_SUCH_GOODS"),
    REFUND_TIME_OUT("110002", "REFUND_TIME_OUT"),
    WAIT_REFUND("110003","WAIT REFUND");


    private String code;
    private String desc;

    StatusCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
