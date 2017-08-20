package com.mohress.training.util;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.mohress.training.dto.Response;
import com.mohress.training.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static com.mohress.training.enums.ResultCode.UNKNOWN_EXCEPTION;

/**
 * 全局异常处理器
 *
 * Created by youtao.wan on 2017/8/9.
 */
@Slf4j
@ControllerAdvice(basePackages = "com.mohress.training.controller")
public class GlobalExceptionHandler {

    private static final Joiner JOINER = Joiner.on(",").skipNulls();

    /**
     * 业务异常处理
     *
     * @param request HTTP请求
     * @param exception 业务异常
     * @return 业务异常响应结果
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public Response businessExceptionHandler(HttpServletRequest request, BusinessException exception){
        String requestUrl = request.getRequestURI();
        String requestBody = joinRequestParameter(request);

        log.error("请求接口业务异常。requestUrl={}, requestBody={}", requestUrl, requestBody, exception);

        Response response = new Response();
        response.setCode(exception.getResultCode().getCode());
        response.setMessage(exception.getResultDesc());
        return response;
    }

    /**
     * 未知异常处理
     *
     * @param request HTTP请求
     * @param throwable 未知异常
     * @return 内部错误响应结果
     */
    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public Response unknownExceptionHandler(HttpServletRequest request, Throwable throwable){
        String requestUrl = request.getRequestURI();
        String requestBody = joinRequestParameter(request);

        log.error("请求接口未知异常。requestURL={}， requestBody={}", requestUrl, requestBody, throwable);

        Response response = new Response();
        response.setCode(UNKNOWN_EXCEPTION.getCode());
        response.setMessage("服务器内部错误，请稍后重试。");
        return response;
    }

    /**
     * 拼接请求参数
     *
     * @param request
     * @return
     */
    private String joinRequestParameter(HttpServletRequest request){
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String[]> it: request.getParameterMap().entrySet()){
            builder.append(it.getKey());
            builder.append("=");
            JOINER.appendTo(builder, it.getValue());
            builder.append("&");
        }
        String requestBody = builder.toString();
        return Strings.isNullOrEmpty(requestBody) ? "" : requestBody.substring(0, requestBody.length()-1);
    }
}
