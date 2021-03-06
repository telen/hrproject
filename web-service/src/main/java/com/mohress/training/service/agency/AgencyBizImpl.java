package com.mohress.training.service.agency;

import com.google.common.base.Preconditions;
import com.mohress.training.dto.QueryDto;
import com.mohress.training.dto.agency.AgencyItemDto;
import com.mohress.training.dto.agency.AgencyRequestDto;
import com.mohress.training.entity.TblTeacher;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.service.BaseManageService;
import com.mohress.training.service.ModuleBiz;
import com.mohress.training.service.teacher.TeacherQuery;
import com.mohress.training.service.teacher.TeacherServiceImpl;
import com.mohress.training.util.Checker;
import com.mohress.training.util.Convert;
import com.mohress.training.util.JsonUtil;
import com.mohress.training.util.SequenceCreator;
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
    private BaseManageService agencyServiceImpl;
    @Resource
    private TeacherServiceImpl teacherServiceImpl;

    @Override
    public void newModule(String o, String agencyId) {
        Preconditions.checkNotNull(o);
        AgencyRequestDto agencyRequestDto;
        try {
            agencyRequestDto = JsonUtil.getInstance().convertToBean(AgencyRequestDto.class, String.valueOf(o));
        } catch (Exception e) {
            throw new RuntimeException("机构反序列化失败");
        }
        Checker.checkNewAgency(agencyRequestDto);
        agencyServiceImpl.newModule(buildInsertTblAgency(agencyRequestDto));
    }

    @Override
    public void delete(List<String> ids) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(ids));
        agencyServiceImpl.delete(ids);
    }

    @Override
    public void update(String o) {
        Preconditions.checkNotNull(o);
        AgencyRequestDto agencyRequestDto;
        try {
            agencyRequestDto = JsonUtil.getInstance().convertToBean(AgencyRequestDto.class, String.valueOf(o));
        } catch (Exception e) {
            throw new RuntimeException("机构反序列化失败");
        }
        agencyServiceImpl.update(buildUpdateTblAgency(agencyRequestDto));
    }

    @Override
    public Object query(QueryDto pageDto) {
        Preconditions.checkNotNull(pageDto);
        Preconditions.checkArgument(pageDto.getPage() >= 0);
        Preconditions.checkArgument(pageDto.getPageSize() > 0);
        List<TblAgency> tblAgencies = agencyServiceImpl.query(buildAgencyQuery(pageDto));

        List<AgencyItemDto> agencyItemDtos = Convert.convertAgency(tblAgencies);
        if(CollectionUtils.isEmpty(agencyItemDtos)){
            return agencyItemDtos;
        }

        for (AgencyItemDto dto : agencyItemDtos) {
            TeacherQuery query = new TeacherQuery();
            query.setPageIndex(0);
            query.setPageSize(Integer.MAX_VALUE);
            query.setAgencyId(pageDto.getAgencyId());
            List<TblTeacher> teachers = teacherServiceImpl.query(query);
            if (!CollectionUtils.isEmpty(teachers)) {
                dto.setEmployeesCount(teachers.size());
            }
        }
        return agencyItemDtos;
    }

    @Override
    public void checkDelete(String agencyId, List<String> ids) {
        return;
    }

    private AgencyQuery buildAgencyQuery(QueryDto dto) {
        return new AgencyQuery(dto.getPageSize(), dto.getPage());
    }

    private TblAgency buildInsertTblAgency(AgencyRequestDto agencyRequestDto) {
        TblAgency agency = new TblAgency();
        BeanUtils.copyProperties(agencyRequestDto, agency);
        agency.setAgencyId(SequenceCreator.getAgencyId());
        return agency;
    }

    private TblAgency buildUpdateTblAgency(AgencyRequestDto agencyRequestDto) {
        TblAgency agency = new TblAgency();
        BeanUtils.copyProperties(agencyRequestDto, agency);
        return agency;
    }

}
