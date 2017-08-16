package com.mohress.training.service.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by youtao.wan on 2017/8/16.
 */
public class LoginFailHandler implements AuthenticationFailureHandler {

    private AuthenticationFailHandler handler = new AuthenticationFailHandler();

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        handler.handle(httpServletRequest, httpServletResponse, e);
    }
}
