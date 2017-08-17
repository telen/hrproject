package com.mohress.training.service.security.handler;

import com.mohress.training.dao.TblAccountDao;
import com.mohress.training.dto.Response;
import com.mohress.training.entity.security.TblAccount;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.util.writer.JsonResponseWriter;
import com.mohress.training.util.writer.Writer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 登录成功处理器
 *
 */
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private TblAccountDao accountDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 1.更新登录时间和登录Ip
        String loginIp = request.getRemoteAddr();
        String userName = authentication.getName();
        Date loginTime = new Date();

        accountDao.updateLogin(userName, loginIp, loginTime);

        // 2.添加cookie
        TblAccount account = accountDao.selectByAccount(userName);

        Cookie cookie = new Cookie("token", account.getUserId());
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        response.addCookie(cookie);

        Writer writer = new JsonResponseWriter(response);
        writer.write(new Response<>(ResultCode.SUCCESS.getCode(), "用户登录成功"));
    }
}
