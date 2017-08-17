package com.mohress.training.service.agency.impl;

import com.google.common.base.Verify;
import com.mohress.training.dao.TblAgencyDao;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.service.agency.AgencyQuery;
import com.mohress.training.service.agency.AgencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 机构服务
 * Created by qx.wang on 2017/8/15.
 */
@Slf4j
@Service
public class AgencyServiceImpl implements AgencyService {

    private static final int DELETE_STATUS = 1;

    @Resource
    private TblAgencyDao tblAgencyDao;

    @Override
    public void newAgency(TblAgency tblAgency) {
        Verify.verify(tblAgencyDao.insertSelective(tblAgency) > 0, "新增机构SQL异常");
    }

    @Override
    @Transactional
    public void deleteAgency(List<String> agencyIds) {
        for (String agencyId : agencyIds) {
            Verify.verify(tblAgencyDao.updateStatus(agencyId, DELETE_STATUS) > 0, "删除机构SQL异常");
        }
    }

    @Override
    public void updateAgency(TblAgency tblAgency) {
        Verify.verify(tblAgencyDao.updateByPrimaryKeySelective(tblAgency) > 0, "更新机构SQL异常");
    }

    @Override
    public List<TblAgency> queryAgencies(AgencyQuery query) {
        return tblAgencyDao.queryAgencyList(query);
    }
}
