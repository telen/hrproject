package com.mohress.training.dao;

import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.service.agency.AgencyQuery;

import java.util.List;

/**
 * 机构
 * Created by qx.wang on 2017/8/15.
 */
public interface TblAgencyDao {

    int insertSelective(TblAgency tblAgency);

    int updateSelective(TblAgency tblAgency);

    int updateStatus(String agencyId,int toStatus);

    List<TblAgency> queryAgencyList(AgencyQuery query);
}
