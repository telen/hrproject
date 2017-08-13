package com.mohress.training.exception;

import com.mohress.training.enums.ResultCode;

/**
 * 自定义业务异常
 *
 */
public class BusinessException extends RuntimeException{

    private ResultCode resultCode;

    private String resultDesc;

    public BusinessException(ResultCode resultCode, String resultDesc) {
        super(resultDesc);
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
    }

    public BusinessException(ResultCode resultCode, String resultDesc, String message, Throwable cause) {
        super(message, cause);
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
    }

    public BusinessException(ResultCode resultCode, String resultDesc, Throwable cause) {
        super(cause);
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }
}
