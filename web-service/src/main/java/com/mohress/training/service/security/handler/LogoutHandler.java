package com.mohress.training.service.security.handler;

import com.mohress.training.dto.Response;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.util.writer.JsonResponseWriter;
import com.mohress.training.util.writer.Writer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录处理器
 *
 */
public class LogoutHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Writer writer = new JsonResponseWriter(response);
        writer.write(new Response(ResultCode.SUCCESS.getCode(), "退出登录成功"));
    }
}
