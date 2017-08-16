package com.mohress.training.service.agency;

import com.mohress.training.entity.agency.TblAgency;

import java.util.List;

/**
 * 机构服务
 * Created by qx.wang on 2017/8/15.
 */
public interface AgencyService {

    /**
     * 新增机构
     */
    void newAgency(TblAgency tblAgency);

    /**
     * 批量删除机构
     * @param agencyIds 机构ID列表
     */
    void deleteAgency(List<String> agencyIds);

    /**
     * 更新agency
     */
    void updateAgency(TblAgency tblAgency);

    /**
     * 查询
     */
    List<TblAgency> queryAgencies(AgencyQuery query);
}
