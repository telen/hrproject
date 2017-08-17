package com.mohress.training.service.security.listenter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.event.AuthorizedEvent;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * 授权成功监听器
 *
 * Created by youtao.wan on 2017/8/9.
 */
@Slf4j
@Component
public class AuthorizeSuccessListener implements ApplicationListener<AuthorizedEvent>{

    public void onApplicationEvent(AuthorizedEvent authorizedEvent) {
        // 1.解析资源路径
        FilterInvocation invocation = (FilterInvocation) authorizedEvent.getSource();
        String url = invocation.getRequestUrl();
        int index = url.indexOf("?");
        if (index != -1) {
            url = url.substring(0, index);
        }

        // 2.解析访问资源权限
        String authorityName = "";
        Collection<ConfigAttribute> collection = authorizedEvent.getConfigAttributes();
        if (!CollectionUtils.isEmpty(collection)){
            for (ConfigAttribute it : collection){
                authorityName = it.getAttribute();
                break;
            }
        }

        // 3.获取用户名
        String userName = authorizedEvent.getAuthentication().getName();

        log.info("{}对{}执行了{}", userName, url, authorityName);
    }
}
