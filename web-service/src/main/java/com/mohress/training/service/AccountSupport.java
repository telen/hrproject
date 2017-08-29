package com.mohress.training.service;

import com.google.common.base.Preconditions;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.entity.security.TblRole;
import com.mohress.training.service.security.AccountManager;
import com.mohress.training.util.AccountAuthority;
import com.mohress.training.util.BusiVerify;
import com.mohress.training.util.RoleAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Created by qx.wang on 2017/8/29.
 */
@Component
public class AccountSupport {

    private static final String OFFICIAL_ROLE_ID = "17081610225621055996";
    private static final String ROOT_ROLE_ID = "17081610225621055995";

    @Resource
    private AccountManager accountManager;

    public String getAgencyId(String userId){
        if (!checkHighAuthority(userId)) {
            TblAgency agency = accountManager.queryAgencyByUserId(userId);
            Preconditions.checkNotNull(agency);
            return agency.getAgencyId();
        }
        return null;
    }

    public boolean checkHighAuthority(String userId) {
        AccountAuthority accountAuthority = accountManager.queryAccountAuthorityByUserId(userId);
        BusiVerify.verifyNotNull(accountAuthority, String.format("用户对应账户为空%s", userId));
        BusiVerify.verify(!CollectionUtils.isEmpty(accountAuthority.getAuthorityList()), String.format("用户对应账户角色为空%s", userId));

        List<RoleAuthority> authorityList = accountAuthority.getAuthorityList();
        for (RoleAuthority authority : authorityList) {
            TblRole role = authority.getRole();
            if (role == null) {
                continue;
            }
            if (OFFICIAL_ROLE_ID.equals(role.getRoleId()) || ROOT_ROLE_ID.equals(role.getRoleId())) {
                return true;
            }
        }
        return false;
    }
}
