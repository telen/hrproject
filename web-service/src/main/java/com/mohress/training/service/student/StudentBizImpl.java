package com.mohress.training.service.student;

import com.google.common.base.Preconditions;
import com.mohress.training.dto.QueryDto;
import com.mohress.training.dto.student.StudentRequestDto;
import com.mohress.training.entity.student.TblStudent;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;
import com.mohress.training.service.BaseManageService;
import com.mohress.training.service.ModuleBiz;
import com.mohress.training.util.Checker;
import com.mohress.training.util.Convert;
import com.mohress.training.util.JsonUtil;
import com.mohress.training.util.SequenceCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 教师服务
 * Created by qx.wang on 2017/8/15.
 */
@Slf4j
@Service
public class StudentBizImpl implements ModuleBiz {

    @Resource
    private BaseManageService studentServiceImpl;

    @Override
    public void newModule(String o, String agencyId) {
        Preconditions.checkArgument(o != null);
        StudentRequestDto studentRequestDto;
        try {
            studentRequestDto = JsonUtil.getInstance().convertToBean(StudentRequestDto.class, String.valueOf(o));
        } catch (Exception e) {
            log.error("教师新增反序列化失败 {}", o, e);
            throw new BusinessException(ResultCode.FAIL, "反序列化失败");
        }

        Checker.checkNewStudent(studentRequestDto);
        studentServiceImpl.newModule(buildInsertTblStudent(studentRequestDto, agencyId));
    }

    @Override
    public void delete(List<String> ids) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(ids));
        studentServiceImpl.delete(ids);
    }

    @Override
    public void update(String o) {
        Preconditions.checkArgument(o != null);
        StudentRequestDto studentRequestDto;
        try {
            studentRequestDto = JsonUtil.getInstance().convertToBean(StudentRequestDto.class, String.valueOf(o));
        } catch (Exception e) {
            log.error("教师新增反序列化失败 {}", o, e);
            throw new RuntimeException("反序列化失败");
        }
        studentServiceImpl.update(buildUpdateTblStudent(studentRequestDto));
    }

    @Override
    public Object query(QueryDto pageDto) {
        Preconditions.checkNotNull(pageDto);
        Preconditions.checkArgument(pageDto.getPage() >= 0);
        Preconditions.checkArgument(pageDto.getPageSize() > 0);

        List<TblStudent> tblStudents = studentServiceImpl.query(buildStudentQuery(pageDto));

        return Convert.convertStudent(tblStudents);
    }

    @Override
    public void checkDelete(String agencyId, List<String> ids) {
    }

    private StudentQuery buildStudentQuery(QueryDto dto) {
        StudentQuery query = new StudentQuery();
        query.setPageIndex(dto.getPage());
        query.setPageSize(dto.getPageSize());
        query.setCourseId(dto.getCourseId());
        query.setClassId(dto.getClassId());
        query.setAgencyId(dto.getAgencyId());
        return query;
    }

    private TblStudent buildInsertTblStudent(StudentRequestDto studentRequestDto,String agencyId) {
        TblStudent student = new TblStudent();
        BeanUtils.copyProperties(studentRequestDto, student);
        student.setAgencyId(agencyId);
        student.setStudentId(SequenceCreator.getStudentId());
        Long birthday = studentRequestDto.getBirthday();
        Long schoolDate = studentRequestDto.getSchoolDate();
        Long dropout = studentRequestDto.getDropout();
        if (birthday != null) {
            student.setBirthday(new Date(birthday));
        }
        if (schoolDate != null) {
            student.setSchoolDate(new Date(schoolDate));
        }
        if (dropout != null) {
            student.setDropout(new Date(dropout));
        }
        return student;
    }

    private TblStudent buildUpdateTblStudent(StudentRequestDto studentRequestDto) {
        TblStudent student = new TblStudent();
        BeanUtils.copyProperties(studentRequestDto, student);
        Long birthday = studentRequestDto.getBirthday();
        Long schoolDate = studentRequestDto.getSchoolDate();
        Long dropout = studentRequestDto.getDropout();
        if (birthday != null) {
            student.setBirthday(new Date(birthday));
        }
        if (schoolDate != null) {
            student.setSchoolDate(new Date(schoolDate));
        }
        if (dropout != null) {
            student.setDropout(new Date(dropout));
        }
        return student;
    }

}
