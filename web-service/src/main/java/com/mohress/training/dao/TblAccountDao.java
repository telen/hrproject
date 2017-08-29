package com.mohress.training.dao;

import com.mohress.training.entity.security.TblAccount;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 用户账号
 */
public interface TblAccountDao {

    int insert(TblAccount tblAccount);

    int updatePassword(String account, String password);

    int updateExpiredTime(String account, Date expiredTime);

    int updateEnable(String account, boolean enable);

    int updateLocked(String account, boolean locked);

    int updateLogin(@Param("account") String account, @Param("loginIp") String loginIp, @Param("loginTime") Date loginTime);

    TblAccount selectByUserId(String userId);

    TblAccount selectByAccount(String account);

    List<TblAccount> selectByRoleId(String roleId);
}
