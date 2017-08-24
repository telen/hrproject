package com.mohress.training.dao;

import com.mohress.training.entity.agency.TblAccountAgency;

/**
 * 账号与培训机构关联实体数据接口
 *
 */
public interface TblAccountAgencyDao {

    TblAccountAgency selectByUserId(String userId);
}
