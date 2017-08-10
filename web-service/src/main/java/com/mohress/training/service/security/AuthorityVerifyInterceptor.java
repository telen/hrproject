package com.mohress.training.service.security;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;

import javax.annotation.Resource;
import javax.servlet.*;
import java.io.IOException;

/**
 * 权限校验拦截器
 *
 */
public class AuthorityVerifyInterceptor extends AbstractSecurityInterceptor implements Filter{

    @Resource
    private SecurityMetadataSource resourceAuthorityLoadingService;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void destroy() {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        FilterInvocation invocation = new FilterInvocation(servletRequest, servletResponse, filterChain);

        // 资源访问的权限校验
        InterceptorStatusToken token = super.beforeInvocation(invocation);
        try {
            invocation.getChain().doFilter(invocation.getRequest(), invocation.getResponse());
            super.afterInvocation(token, null);
        }finally {
            super.finallyInvocation(token);
        }
    }

    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return resourceAuthorityLoadingService;
    }

    public SecurityMetadataSource getResourceAuthorityLoadingService() {
        return resourceAuthorityLoadingService;
    }

    public void setResourceAuthorityLoadingService(SecurityMetadataSource resourceAuthorityLoadingService) {
        this.resourceAuthorityLoadingService = resourceAuthorityLoadingService;
    }
}
