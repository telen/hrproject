package com.mohress.training.dao;

import com.mohress.training.entity.TblSCRelation;

/**
 * Created by qx.wang on 2017/8/17.
 */
public interface TblSCRelationDao {
    int insertSelective(TblSCRelation relation);
}
