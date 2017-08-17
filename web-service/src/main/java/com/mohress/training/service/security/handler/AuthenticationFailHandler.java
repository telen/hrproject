package com.mohress.training.service.security.handler;

import com.mohress.training.dto.Response;
import com.mohress.training.util.SerializerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.mohress.training.enums.ResultCode.FAIL;

/**
 * 账号验证失败处理器
 *
 */
@Slf4j
public class AuthenticationFailHandler {

    public void handle(HttpServletRequest request, HttpServletResponse response, AuthenticationException e){
        if (!response.isCommitted()){
            response.setStatus(403);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");

            Response accessDeniedResponse = new Response(FAIL.getCode(), "账号密码验证不通过");
            String json = SerializerFactory.defaultSerializer().serialize(accessDeniedResponse);

            PrintWriter writer = null;
            try {
                writer = response.getWriter();
                writer.append(json);
            }catch (IOException ie){
                log.error("账号授权异常处理器数据写入异常。", ie);
            }finally {
                if (writer != null){
                    writer.flush();
                    writer.close();
                }
            }
        }
    }
}
