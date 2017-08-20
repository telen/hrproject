package com.mohress.training.dao;

import com.mohress.training.entity.security.TblAuthority;

import java.util.List;

/**
 * Created by youtao.wan on 2017/8/10.
 */
public interface TblAuthorityDao {

    int insert(TblAuthority tblAuthority);

    TblAuthority selectByAuthorityId(String authorityId);

    List<TblAuthority> selectAll();
}
