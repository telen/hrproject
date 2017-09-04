package com.mohress.training.dao;

import com.mohress.training.entity.security.TblAction;

import java.util.List;

/**
 * 权限操作接口
 *
 */
public interface TblActionDao {

    int insert(TblAction tblAction);

    List<TblAction> selectByAuthorityId(String authorityId);

    List<TblAction> selectAll();
}
