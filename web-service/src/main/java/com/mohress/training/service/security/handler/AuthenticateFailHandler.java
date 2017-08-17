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
 */
@Slf4j
public class AuthenticateFailHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.error("账号验证失败。", e);

        Writer writer = new JsonResponseWriter(response);
        writer.write(new Response(FAIL.getCode(), "用户尚未登录，请重新登录"));
    }
}
