package com.mohress.training.service.security;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mohress.training.entity.security.TblAccount;
import com.mohress.training.entity.security.TblAction;
import com.mohress.training.util.AccountAuthority;
import com.mohress.training.util.AuthorityAction;
import com.mohress.training.util.DateUtil;
import com.mohress.training.util.RoleAuthority;
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

/**
 * 加载账号信息和账号权限信息
 *
 */
@Slf4j
public class AccountSecurityService implements UserDetailsService{

    @Resource
    private AccountManager accountManager;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (Strings.isNullOrEmpty(s)){
            throw new UsernameNotFoundException("登录账号为空");
        }

        AccountAuthority accountAuthority = accountManager.queryAccountAuthorityByAccount(s);

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
        List<RoleAuthority> roleAuthorityList = accountAuthority.getAuthorityList();
        if(CollectionUtils.isEmpty(roleAuthorityList)){
            return ImmutableList.of();
        }

        // 加载角色关联的权限Id
        List<GrantedAuthority> authorityList = Lists.newArrayList();
        for (RoleAuthority it: roleAuthorityList){
            if (CollectionUtils.isEmpty(it.getAuthorityAction())){
                continue;
            }

            for (AuthorityAction authorityAction : it.getAuthorityAction()){
                if (CollectionUtils.isEmpty(authorityAction.getActionList())){
                    continue;
                }
                for (TblAction action: authorityAction.getActionList()){
                    authorityList.add(new SimpleGrantedAuthority(action.getActionName()));
                }
            }
        }

        // 默认加载每个用户都具备的权限Id
        authorityList.addAll(loadDefaultAuthority());
        return authorityList;
    }

    /**
     * 加载用户默认拥有的权限
     *
     * @return
     */
    private List<GrantedAuthority> loadDefaultAuthority(){
        List<GrantedAuthority> defaultAuthority = Lists.newArrayList();
        defaultAuthority.add(new SimpleGrantedAuthority("ACTION_USER_QUERY"));
        defaultAuthority.add(new SimpleGrantedAuthority("ACTION_SECURITY_MENUS"));
        return defaultAuthority;
    }
}
