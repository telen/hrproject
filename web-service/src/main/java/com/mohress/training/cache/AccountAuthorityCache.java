package com.mohress.training.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.ForwardingLoadingCache;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mohress.training.dao.*;
import com.mohress.training.util.AccountAuthority;
import com.mohress.training.util.AuthorityAction;
import com.mohress.training.util.RoleAuthority;
import com.mohress.training.entity.security.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by youtao.wan on 2017/8/10.
 */
@Slf4j
@Component
public class AccountAuthorityCache extends ForwardingLoadingCache<String, AccountAuthority> {

    private LoadingCache<String, AccountAuthority> cache;

    @Resource
    private TblAccountDao tblAccountDao;

    @Resource
    private TblAccountRoleDao tblAccountRoleDao;

    @Resource
    private TblRoleDao tblRoleDao;

    @Resource
    private TblRoleAuthorityDao tblRoleAuthorityDao;

    @Resource
    private TblAuthorityDao tblAuthorityDao;

    @Resource
    private TblActionDao tblActionDao;

    public AccountAuthorityCache() {
        cache = CacheBuilder.newBuilder()
                .maximumSize(1000).expireAfterAccess(30, TimeUnit.MINUTES)
                .build(new CacheLoader<String, AccountAuthority>() {
                    @Override
                    public AccountAuthority load(String s) throws Exception {
                        return loadAccountAuthority(s);
                    }
                });
    }

    public AccountAuthorityCache(LoadingCache<String, AccountAuthority> cache) {
        this.cache = cache;
    }

    protected LoadingCache<String, AccountAuthority> delegate() {
        return cache;
    }

    /**
     * 加载账号权限
     *
     * @param account
     * @return
     */
    private AccountAuthority loadAccountAuthority(String account){
        TblAccount tblAccount = tblAccountDao.selectByAccount(account);
        if (!isEnable(tblAccount)){
            return null;
        }

        List<TblAccountRole> accountRoleList = tblAccountRoleDao.selectByUserId(tblAccount.getUserId());
        if (CollectionUtils.isEmpty(accountRoleList)){
            return new AccountAuthority(tblAccount, ImmutableSet.<RoleAuthority>of());
        }

        Set<RoleAuthority> roleAuthoritySet = Sets.newHashSet();
        for (TblAccountRole it : accountRoleList){
            RoleAuthority roleAuthority = loadRoleAuthority(it.getRoleId());
            if (roleAuthority != null){
                roleAuthoritySet.add(roleAuthority);
            }

        }
        return new AccountAuthority(tblAccount, roleAuthoritySet);
    }

    /**
     * 加载角色权限
     *
     * @param roleId
     * @return
     */
    private RoleAuthority loadRoleAuthority(String roleId){
        TblRole role = tblRoleDao.selectByRoleId(roleId);
        if (!isEnable(role)){
            return null;
        }

        // 1.加载角色本身权限
        Set<AuthorityAction> authoritySet = Sets.newHashSet(loadAuthority(roleId));
        // 2.加载子角色权限
        authoritySet.addAll(loadChildRoleAuthority(role));
        return new RoleAuthority(role, authoritySet);
    }

    /**
     * 加载子角色权限
     *
     * @param parentRole
     * @return
     */
    private Set<AuthorityAction> loadChildRoleAuthority(TblRole parentRole){
        Set<AuthorityAction> childRoleAuthority = Sets.newHashSet();

        List<TblRole> childRoleList = tblRoleDao.selectByParentRoleId(parentRole.getRoleId());
        for (TblRole childRole : childRoleList){
            if (isEnable(childRole) && childRole.getPriority() > parentRole.getPriority()){
                childRoleAuthority.addAll(loadAuthority(childRole.getRoleId()));
                childRoleAuthority.addAll(loadChildRoleAuthority(childRole));
            }
        }
        return childRoleAuthority;
    }

    /**
     * 加载角色关联权限
     *
     * @param roleId
     * @return
     */
    private Set<AuthorityAction> loadAuthority(String roleId){
        List<TblRoleAuthority> roleAuthorityList = tblRoleAuthorityDao.selectByRoleId(roleId);
        if (CollectionUtils.isEmpty(roleAuthorityList)){
            return ImmutableSet.of();
        }

        Set<AuthorityAction> authoritySet = Sets.newHashSet();
        for (TblRoleAuthority it: roleAuthorityList){
            // 判断权限是否可用
            TblAuthority tblAuthority = tblAuthorityDao.selectByAuthorityId(it.getAuthorityId());
            if (!isEnable(tblAuthority)){
                continue;
            }

            // 加载与权限关联的动作信息
            TblAction tblAction = tblActionDao.selectByAuthorityId(tblAuthority.getAuthorityId());
            authoritySet.add(new AuthorityAction(tblAuthority, tblAction));
        }
        return authoritySet;
    }

    /**
     * 判断对象是否可用
     *
     * @param enableObject
     * @return
     */
    private boolean isEnable(Object enableObject){
        if (enableObject == null){
            return false;
        }
        try {
            Field enableField = enableObject.getClass().getDeclaredField("enable");
            enableField.setAccessible(true);
            return enableField.getBoolean(enableObject);
        } catch (Exception e){
            return false;
        }
    }
}
