package com.mohress.training.dto;

import com.mohress.training.enums.ResultCode;

/**
 * 返回值描述
 *
 * Created by qx.wang on 2017/4/10.
 */
public class Responses {

    public static <T> Response<T> fail() {
        return fail(ResultCode.UNKNOWN_EXCEPTION);
    }

    public static <T> Response<T> fail(ResultCode resultCode) {
        return fail(resultCode.getCode(), resultCode.getDesc());
    }


    public static <T> Response<T> fail(String retCode, String retMsg) {
        return new Response<>(retCode, retMsg, null);
    }

    public static <T> Response<T> succ(T data) {
        return new Response<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getDesc(), data);
    }

}
