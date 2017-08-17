package com.mohress.training.controller;

import com.mohress.training.dto.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * exception handler
 * Created by qx.wang on 2017/8/16.
 */
public abstract class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler
    @ResponseBody
    public Object handleException(Throwable ex, HttpServletResponse response) {
        logger.error("Controller处理请求出现未知异常", ex);
        return Responses.fail();
    }

}