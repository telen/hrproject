package com.mohress.training.service.security;

import com.mohress.training.cache.AccountAuthorityCache;
import com.mohress.training.dao.TblAccountAgencyDao;
import com.mohress.training.dao.TblAccountDao;
import com.mohress.training.dao.TblAgencyDao;
import com.mohress.training.entity.agency.TblAccountAgency;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.entity.security.TblAccount;
import com.mohress.training.util.AccountAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 账号管理
 *
 */
@Component
public class AccountManager {

    @Resource
    private AccountAuthorityCache cache;

    @Resource
    private TblAccountDao tblAccountDao;

    @Resource
    private TblAccountAgencyDao tblAccountAgencyDao;

    @Resource
    private TblAgencyDao tblAgencyDao;

    /**
     * 根据用户Id查询用户信息及用户权限信息
     *
     * @param userId
     * @return
     */
    public AccountAuthority queryAccountAuthorityByUserId(String userId){
        TblAccount account = tblAccountDao.selectByUserId(userId);
        if (account != null){
            return queryAccountAuthorityByAccount(account.getAccount());
        }
        return null;
    }

    /**
     * 根据账号信息查询用户信息及用户权限信息
     *
     * @param account
     * @return
     */
    public AccountAuthority queryAccountAuthorityByAccount(String account){
        try {
            return cache.get(account);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 通过UserId 查询用户的培训机构信息
     *
     * @param userId
     * @return
     */
    public TblAgency queryAgencyByUserId(String userId){
        TblAccountAgency tblAccountAgency = tblAccountAgencyDao.selectByUserId(userId);
        if (tblAccountAgency != null){
            return tblAgencyDao.selectByAgencyId(tblAccountAgency.getAgencyId());
        }
        return null;
    }
}
