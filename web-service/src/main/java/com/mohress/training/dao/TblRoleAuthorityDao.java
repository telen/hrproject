package com.mohress.training.dao;

import com.mohress.training.entity.security.TblRoleAuthority;

import java.util.List;

/**
 * Created by youtao.wan on 2017/8/10.
 */
public interface TblRoleAuthorityDao {

    int insert(TblRoleAuthority tblRoleAuthority);

    List<TblRoleAuthority> selectByRoleId(String roleId);

    List<TblRoleAuthority> selectByAuthorityId(String authorityId);
}
