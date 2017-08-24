package com.mohress.training.dao;

import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.service.agency.AgencyQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;

/**
 * 机构
 * Created by qx.wang on 2017/8/15.
 */
public interface TblAgencyDao {

    int insertSelective(TblAgency tblAgency);

    int updateByPrimaryKeySelective(TblAgency tblAgency);

    int updateStatus(@Param("agencyId") String agencyId, @Param("toStatus") int toStatus);

    List<TblAgency> queryAgencyList(AgencyQuery query);

    int updateByPrimaryAgencyIdSelective(TblAgency tblAgency);

    TblAgency selectByAgencyId(String agencyId);
}
