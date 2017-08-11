package com.mohress.training.dao;

import com.mohress.training.entity.security.TblAccountRole;

import java.util.List;

/**
 * Created by youtao.wan on 2017/8/9.
 */
public interface TblAccountRoleDao {

    int insert(TblAccountRole tblAccountRole);

    int delete(Long id);

    int updateByParmarilyKey(TblAccountRole tblAccountRole);

    List<TblAccountRole> selectByUserId(String userId);

    List<TblAccountRole> selectByRoleId(String roleId);
}
