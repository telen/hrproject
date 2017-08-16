package com.mohress.training.service.security.listenter;

import com.mohress.training.dao.TblAccountDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 账号登录成功监听器
 * 修改登录时间,登录Ip
 *
 */
@Slf4j
@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent>{

    @Resource
    private TblAccountDao accountDao;

    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        // 此处的类型转化需要看源码
        Authentication authentication = authenticationSuccessEvent.getAuthentication();
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
        WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails)token.getDetails();

        String loginIp = webAuthenticationDetails.getRemoteAddress();
        String userName = authentication.getName();
        Date loginTime = new Date();

        // 更新登录时间和登录Ip
        accountDao.updateLogin(userName, loginIp, loginTime);
    }
}
