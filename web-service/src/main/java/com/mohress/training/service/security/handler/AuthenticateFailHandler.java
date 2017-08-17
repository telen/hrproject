package com.mohress.training.service.security.handler;

import com.mohress.training.dto.Response;
import com.mohress.training.util.writer.JsonResponseWriter;
import com.mohress.training.util.writer.Writer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.mohress.training.enums.ResultCode.FAIL;

/**
 * 账号信息验证失败处理器
 *
 * Created by youtao.wan on 2017/8/16.
 */
@Slf4j
public class AuthenticateFailHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        Writer writer = new JsonResponseWriter(response);
        writer.write(new Response(FAIL.getCode(), "账号密码验证不通过"));
    }
}
