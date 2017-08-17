package com.mohress.training.service.security;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mohress.training.cache.AccountAuthorityCache;
import com.mohress.training.util.AccountAuthority;
import com.mohress.training.util.RoleAuthority;
import com.mohress.training.entity.security.TblAccount;
import com.mohress.training.entity.security.TblAuthority;
import com.mohress.training.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 加载账号信息和账号权限信息
 *
 */
@Slf4j
public class AccountSecurityService implements UserDetailsService{

    @Resource
    private AccountAuthorityCache cache;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (Strings.isNullOrEmpty(s)){
            throw new UsernameNotFoundException("登录账号为空");
        }

        AccountAuthority accountAuthority;
        try {
            accountAuthority = cache.get(s);
        } catch (Exception e) {
            throw new UsernameNotFoundException("加载账号信息失败，请稍后重试");
        }
        if(accountAuthority == null){
            throw new UsernameNotFoundException(s + "不存在");
        }

        List<GrantedAuthority> authorityList = loadAuthority(accountAuthority);

        TblAccount account = accountAuthority.getAccount();
        boolean isExpired = DateUtil.isBeforeNow(account.getExpiredTime());

        return new User(s, account.getPassword(), account.isEnable(),
                !isExpired,true, !account.isLocked(), authorityList);
    }


    private List<GrantedAuthority> loadAuthority(AccountAuthority accountAuthority){
        Set<RoleAuthority> roleAuthoritySet = accountAuthority.getAuthoritySet();
        if(CollectionUtils.isEmpty(roleAuthoritySet)){
            return ImmutableList.of();
        }

        List<GrantedAuthority> authorityList = Lists.newArrayList();
        for (RoleAuthority it: roleAuthoritySet){
            if (CollectionUtils.isEmpty(it.getAuthoritySet())){
                continue;
            }

            for (TblAuthority authority : it.getAuthoritySet()){
                authorityList.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
            }
        }
        return authorityList;
    }
}
