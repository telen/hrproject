package com.mohress.training.dto;

/**
 * 返回值描述
 *
 * Created by qx.wang on 2017/4/10.
 */
public class Responses {

    public static <T> Response<T> fail() {
        return fail(StatusCode.UNKNOWN_EXCEPTION);
    }

    public static <T> Response<T> fail(StatusCode statusCode) {
        return fail(statusCode.getCode(), statusCode.getDesc());
    }


    public static <T> Response<T> fail(String retCode, String retMsg) {
        return new Response<>(retCode, retMsg, null);
    }

    public static <T> Response<T> succ(T data) {
        return new Response<>(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getDesc(), data);
    }

}
