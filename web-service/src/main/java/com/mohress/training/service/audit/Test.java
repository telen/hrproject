package com.mohress.training.service.audit;

import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * Created by youtao.wan on 2017/8/11.
 */
public class Test {
    AuthenticationFailureHandler test = null;

    UsernamePasswordAuthenticationFilter filter = null;
    ExceptionTranslationFilter s = null;
}
