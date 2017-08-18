package com.mohress.training.service.security.handler;

import com.mohress.training.dao.TblAccountDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
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

    private final String forwardUrl = "login";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 1.更新登录时间和登录Ip
        String loginIp = request.getRemoteAddr();
        String userName = authentication.getName();
        Date loginTime = new Date();

        accountDao.updateLogin(userName, loginIp, loginTime);

        request.setAttribute("userName", userName);
        // 2.转发
        request.getRequestDispatcher(forwardUrl).forward(request, response);
    }
}
