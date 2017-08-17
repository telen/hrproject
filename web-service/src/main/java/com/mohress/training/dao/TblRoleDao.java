package com.mohress.training.dao;

import com.mohress.training.entity.security.TblRole;

import java.util.List;

/**
 * Created by youtao.wan on 2017/8/9.
 */
public interface TblRoleDao {
    int insert(TblRole tblRole);

    TblRole selectByRoleId(String roleId);

    List<TblRole> selectByParentRoleId(String parentRoleId);
}
