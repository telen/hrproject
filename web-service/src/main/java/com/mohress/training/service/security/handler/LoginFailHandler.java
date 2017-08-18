package com.mohress.training.service.security.handler;

import com.mohress.training.dto.Response;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.util.writer.JsonResponseWriter;
import com.mohress.training.util.writer.Writer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.mohress.training.enums.ResultCode.FAIL;

/**
 * 登录失败处理器
 *
 */
public class LoginFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        Writer writer = new JsonResponseWriter(httpServletResponse);
        writer.write(new Response<>(FAIL.getCode(), "密码错误"));
    }
}
