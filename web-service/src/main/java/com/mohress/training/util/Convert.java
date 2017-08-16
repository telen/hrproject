package com.mohress.training.util;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mohress.training.dto.agency.AgencyItemDto;
import com.mohress.training.dto.agency.AgencyListResponseDto;
import com.mohress.training.dto.teacher.TeacherItemDto;
import com.mohress.training.dto.teacher.TeacherListResponseDto;
import com.mohress.training.entity.TblTeacher;
import com.mohress.training.entity.agency.TblAgency;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * bean之间的转换
 * Created by qx.wang on 2017/8/15.
 */
public class Convert {

    /**
     * 转换机构列表展示
     */
    public static AgencyListResponseDto convertAgency(List<TblAgency> tblAgencies) {
        if (CollectionUtils.isEmpty(tblAgencies)) {
            return null;
        }
        return new AgencyListResponseDto(Lists.transform(tblAgencies, new Function<TblAgency, AgencyItemDto>() {
            @Override
            public AgencyItemDto apply(TblAgency input) {
                AgencyItemDto dto = new AgencyItemDto();
                BeanUtils.copyProperties(input, dto);
                return dto;
            }
        }));
    }

    public static TeacherListResponseDto convertTeacher(List<TblTeacher> tblTeachers) {
        if(CollectionUtils.isEmpty(tblTeachers)){
            return null;
        }

        return new TeacherListResponseDto(Lists.transform(tblTeachers, new Function<TblTeacher, TeacherItemDto>() {
            @Override
            public TeacherItemDto apply(TblTeacher input) {
                TeacherItemDto dto = new TeacherItemDto();
                BeanUtils.copyProperties(input,dto);
                return dto;
            }
        }));
    }
}
