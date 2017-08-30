package com.mohress.training.service.security.handler;

import com.mohress.training.dto.Response;
import com.mohress.training.util.writer.JsonResponseWriter;
import com.mohress.training.util.writer.Writer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.mohress.training.enums.ResultCode.FAIL;

/**
 * 访问授权失败处理器
 *
 * Created by youtao.wan on 2017/8/16.
 */
@Slf4j
public class AuthorizeFailHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {

        log.error("访问{}无权限。", request.getRequestURI(), e);

        Writer writer = new JsonResponseWriter(response);
        writer.write(new Response(FAIL.getCode(), "访问无权限"));
    }
}
