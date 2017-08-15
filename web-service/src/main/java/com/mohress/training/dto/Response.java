package com.mohress.training.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * api标准响应格式
 *
 * Created by youtao.wan on 2017/8/9.
 */
@Data
public class Response<T> implements Serializable{
    /**
     * 响应结果码
     */
    private String code;

    /**
     * 结果信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    public Response() {
    }

    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
