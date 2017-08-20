package com.mohress.training.service.security;

import com.google.common.collect.Sets;
import com.mohress.training.dao.TblActionDao;
import com.mohress.training.dao.TblActionDao;
import com.mohress.training.entity.security.TblAction;
import com.mohress.training.entity.security.TblAction;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 资源权限加载服务
 *
 */
public class ResourceAuthorityLoadingService implements FilterInvocationSecurityMetadataSource{

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Resource
    private TblActionDao tblActionDao;

    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        String url = ((FilterInvocation) o).getRequestUrl();

        Set<ConfigAttribute> configAttributeSet = Sets.newHashSet();
        List<TblAction> authorityList = tblActionDao.selectAll();
        for (TblAction it: authorityList){

            if (pathMatcher.match(it.getResourcePath(), url)){
                configAttributeSet.add(new SecurityConfig(it.getActionName()));
            }
        }
        return configAttributeSet;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        List<TblAction> authorityList = tblActionDao.selectAll();

        Set<ConfigAttribute> configAttributeSet = Sets.newHashSet();
        for (TblAction it : authorityList){
            configAttributeSet.add(new SecurityConfig(it.getActionName()));

        }
        return configAttributeSet;
    }

    public boolean supports(Class<?> aClass) {
        return true;
    }
}
