package com.mohress.training.dao;

import com.mohress.training.entity.security.TblAccount;

import java.util.Date;

/**
 * 用户账号
 */
public interface TblAccountDao {

    int insert(TblAccount tblAccount);

    int updatePassword(String account, String password);

    int updateDeadLine(String account, Date deadLine);

    int updateEnable(String account, boolean enable);

    int updateLocked(String account, boolean locked);

    int updateLogin(String account, String loginIp, Date loginTime);

    TblAccount selectByUserId(String userId);

    TblAccount selectByAccount(String account);
}
