package com.mohress.training.service.agency;

import com.google.common.base.Verify;
import com.mohress.training.dao.TblAgencyDao;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.service.BaseManageService;
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
public class AgencyServiceImpl implements BaseManageService {
    private static final int DELETE_STATUS = 1;

    @Resource
    private TblAgencyDao tblAgencyDao;

    @Override
    public <T> void newModule(T t) {
        Verify.verify(tblAgencyDao.insertSelective((TblAgency) t) > 0, "新增机构SQL异常");

    }

    @Override
    public <T> void update(T t) {
        Verify.verify(tblAgencyDao.updateByPrimaryAgencyIdSelective((TblAgency) t) > 0, "更新机构SQL异常");

    }

    @Override
    public <T, M> List<T> query(M m) {
        return (List<T>) tblAgencyDao.queryAgencyList((AgencyQuery) m);

    }

    @Override
    public <T, M> List<T> queryByKeyword(M m) {
        throw new RuntimeException("暂不支持");
    }

    @Override
    @Transactional
    public void delete(List<String> agencyIds) {
        for (String agencyId : agencyIds) {
            Verify.verify(tblAgencyDao.updateStatus(agencyId, DELETE_STATUS) > 0, "删除机构SQL异常");
        }
    }

}
