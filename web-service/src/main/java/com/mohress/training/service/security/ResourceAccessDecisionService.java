package com.mohress.training.service.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * 权限校验具体执行者
 *
 */
@Slf4j
@AllArgsConstructor
public class ResourceAccessDecisionService implements AccessDecisionManager{

    private AccessDecisionVoter<Object> voter;

    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        String url = ((FilterInvocation) o).getRequestUrl();

        if (CollectionUtils.isEmpty(collection)){
            return;
        }

        if (AccessDecisionVoter.ACCESS_GRANTED != voter.vote(authentication, o, collection)){
            throw new AccessDeniedException(String.format("用户[%s]访问[%s]无权限", authentication.getName(), url));
        }
    }

    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    public boolean supports(Class<?> aClass) {
        return true;
    }
}
