package com.mohress.training.util;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mohress.training.dto.agency.AgencyItemDto;
import com.mohress.training.dto.mclass.ClassItemDto;
import com.mohress.training.dto.mclass.ClassRequestDto;
import com.mohress.training.dto.student.StudentItemDto;
import com.mohress.training.dto.teacher.TeacherItemDto;
import com.mohress.training.entity.TblStudent;
import com.mohress.training.entity.TblTeacher;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.entity.mclass.TblClass;
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
    public static List<AgencyItemDto> convertAgency(List<TblAgency> tblAgencies) {
        if (CollectionUtils.isEmpty(tblAgencies)) {
            return null;
        }
        return Lists.transform(tblAgencies, new Function<TblAgency, AgencyItemDto>() {
            @Override
            public AgencyItemDto apply(TblAgency input) {
                AgencyItemDto dto = new AgencyItemDto();
                BeanUtils.copyProperties(input, dto);
                return dto;
            }
        });
    }

    public static List<TeacherItemDto> convertTeacher(List<TblTeacher> tblTeachers) {
        if(CollectionUtils.isEmpty(tblTeachers)){
            return null;
        }

        return Lists.transform(tblTeachers, new Function<TblTeacher, TeacherItemDto>() {
            @Override
            public TeacherItemDto apply(TblTeacher input) {
                TeacherItemDto dto = new TeacherItemDto();
                BeanUtils.copyProperties(input,dto);
                return dto;
            }
        });
    }

    public static List<ClassItemDto> convertClass(List<TblClass> tblClasses) {
        if(CollectionUtils.isEmpty(tblClasses)){
            return null;
        }

        return Lists.transform(tblClasses, new Function<TblClass, ClassItemDto>() {
            @Override
            public ClassItemDto apply(TblClass input) {
                ClassItemDto dto = new ClassItemDto();
                BeanUtils.copyProperties(input,dto);
                return dto;
            }
        });

    }

    public static List<StudentItemDto> convertStudent(List<TblStudent> tblStudents) {
        if(CollectionUtils.isEmpty(tblStudents)){
            return null;
        }

        return Lists.transform(tblStudents, new Function<TblStudent, StudentItemDto>() {
            @Override
            public StudentItemDto apply(TblStudent input) {
                StudentItemDto dto = new StudentItemDto();
                BeanUtils.copyProperties(input,dto);
                return dto;
            }
        });
    }
}
