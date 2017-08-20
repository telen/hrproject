package com.mohress.training.util;

import com.google.common.base.Strings;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;

/**
 * 需要抛出单独的异常， 与guava的Verify, Preconditions分开， 便于异常处理
 *
 * @author yonglong.xiao.
 */
public class BusiVerify {

    /**
     * boolean检验
     *
     * @param mustTrue 必须为true
     */
    public static void verify(boolean mustTrue, ResultCode code, String desc) {
        if (!mustTrue) {
            throw new BusinessException(code, desc);
        }
    }

    /**
     * boolean检验
     *
     * @param mustTrue 必须为true
     */
    public static void verify(boolean mustTrue, String desc) {
        if (!mustTrue) {
            throw new BusinessException(ResultCode.FAIL, desc);
        }
    }

    /**
     * 校验非空
     *
     */
    public static <T> T verifyNotNull(T obj, String desc) {
        verify(obj != null, ResultCode.FAIL, desc);
        return obj;
    }

    /**
     * 字符串非空
     *
     */
    public static String verifyNotEmpty(String obj, String desc) {
        verify(!Strings.isNullOrEmpty(obj), ResultCode.FAIL, desc);
        return obj;
    }
}
