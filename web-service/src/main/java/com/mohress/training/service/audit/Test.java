package com.mohress.training.service.audit;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by youtao.wan on 2017/8/11.
 */
public class Test {
    AuthenticationFailureHandler test = null;

    UsernamePasswordAuthenticationFilter filter = null;
    ExceptionTranslationFilter s = null;

    static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        System.out.println("密码+" + encoder.encode("qx.wang"));
    }
}
