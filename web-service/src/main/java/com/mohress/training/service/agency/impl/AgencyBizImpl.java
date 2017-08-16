package com.mohress.training.service.agency.impl;

import com.google.common.base.Preconditions;
import com.mohress.training.dto.agency.AgencyRequestDto;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.service.ModuleBiz;
import com.mohress.training.service.agency.AgencyService;
import com.mohress.training.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * agency
 * Created by qx.wang on 2017/8/16.
 */
@Slf4j
@Service
public class AgencyBizImpl implements ModuleBiz {

    @Resource
    private AgencyService agencyServiceImpl;

    @Override
    public void newModule(Object o) {
        Preconditions.checkNotNull(o);
        AgencyRequestDto agencyRequestDto = null;
        try {
            agencyRequestDto = JsonUtil.getInstance().convertToBean(AgencyRequestDto.class, String.valueOf(0));
        } catch (Exception e) {
            log.error("新建机构反序列化失败 {}", o, e);
        }
        agencyServiceImpl.newAgency(buildTblAgency(agencyRequestDto));
    }

    @Override
    public void delete(List<String> ids) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(ids));
        agencyServiceImpl.deleteAgency(ids);
    }

    @Override
    public void update(Object o) {
        Preconditions.checkNotNull(o);
        AgencyRequestDto agencyRequestDto = null;
        try {
            agencyRequestDto = JsonUtil.getInstance().convertToBean(AgencyRequestDto.class, String.valueOf(0));
        } catch (Exception e) {
            log.error("新建机构反序列化失败 {}", o, e);
        }
        agencyServiceImpl.updateAgency(buildTblAgency(agencyRequestDto));
    }

    private TblAgency buildTblAgency(AgencyRequestDto agencyRequestDto) {
        TblAgency agency = new TblAgency();
        BeanUtils.copyProperties(agencyRequestDto,agency);
        return agency;
    }

}
