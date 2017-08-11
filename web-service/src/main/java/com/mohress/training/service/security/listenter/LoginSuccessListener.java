package com.mohress.training.service.security.listenter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * Created by youtao.wan on 2017/8/9.
 */
@Slf4j
@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent>{

    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {

    }
}
