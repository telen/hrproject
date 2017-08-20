package com.mohress.training.service.student;

import com.google.common.base.Preconditions;
import com.mohress.training.dto.QueryDto;
import com.mohress.training.dto.student.StudentItemDto;
import com.mohress.training.dto.student.StudentRequestDto;
import com.mohress.training.entity.TblSCRelation;
import com.mohress.training.entity.TblStudent;
import com.mohress.training.service.BaseManageService;
import com.mohress.training.service.ModuleBiz;
import com.mohress.training.service.SCRelationService;
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
 * 教师服务
 * Created by qx.wang on 2017/8/15.
 */
@Slf4j
@Service
public class StudentBizImpl implements ModuleBiz {

    @Resource
    private BaseManageService studentServiceImpl;

    @Override
    public void newModule(String o) {
        Preconditions.checkArgument(o != null);
        StudentRequestDto studentRequestDto = null;
        try {
            studentRequestDto = JsonUtil.getInstance().convertToBean(StudentRequestDto.class, String.valueOf(o));
        } catch (Exception e) {
            log.error("教师新增反序列化失败 {}", o, e);
        }

        Checker.checkNewStudent(studentRequestDto);
        studentServiceImpl.newModule(buildInsertTblStudent(studentRequestDto));
    }

    @Override
    public void delete(List<String> ids) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(ids));
        studentServiceImpl.delete(ids);
    }

    @Override
    public void update(String o) {
        Preconditions.checkArgument(o != null);
        StudentRequestDto studentRequestDto = null;
        try {
            studentRequestDto = JsonUtil.getInstance().convertToBean(StudentRequestDto.class, String.valueOf(o));
        } catch (Exception e) {
            log.error("教师新增反序列化失败 {}", o, e);
        }
        studentServiceImpl.update(buildUpdateTblStudent(studentRequestDto));
    }

    @Override
    public Object query(QueryDto pageDto) {
        Preconditions.checkNotNull(pageDto);
        Preconditions.checkArgument(pageDto.getPage() >= 0);
        Preconditions.checkArgument(pageDto.getPageSize() > 0);
        Preconditions.checkArgument(pageDto.getUserId() != null);

        List<TblStudent> tblStudents = studentServiceImpl.query(buildStudentQuery(pageDto));

        List<StudentItemDto> studentItemDtos = Convert.convertStudent(tblStudents);
        if (CollectionUtils.isEmpty(studentItemDtos)){
            return studentItemDtos;
        }
        for(StudentItemDto dto:studentItemDtos){

        }
        return studentItemDtos;
    }

    @Override
    public Object queryByKeyword(QueryDto queryDto) {
        Preconditions.checkNotNull(queryDto.getKeyword());

        //关联机构名称
        List<TblStudent> tblStudents = studentServiceImpl.queryByKeyword(buildStudentQueryByKey(queryDto));
        return Convert.convertStudent(tblStudents);
    }

    private StudentQuery buildStudentQueryByKey(QueryDto dto) {
        StudentQuery query = new StudentQuery();
        query.setKeyword(dto.getKeyword());
        query.setPageIndex(dto.getPage());
        query.setPageSize(dto.getPageSize());
        return query;
    }

    private StudentQuery buildStudentQuery(QueryDto dto) {
        StudentQuery query = new StudentQuery();
        query.setPageIndex(dto.getPage());
        query.setPageSize(dto.getPageSize());
        return query;
    }

    private SCRelation buildInsertTblStudent(StudentRequestDto studentRequestDto) {
        TblStudent student = new TblStudent();
        BeanUtils.copyProperties(studentRequestDto, student);
        student.setStudentId(SequenceCreator.getStudentId());

        TblSCRelation relation = new TblSCRelation();
        relation.setStudentId(student.getStudentId());
        relation.setCourseId(studentRequestDto.getCourseId());
        return new SCRelation(student, relation);
    }

    private TblStudent buildUpdateTblStudent(StudentRequestDto studentRequestDto) {
        TblStudent student = new TblStudent();
        BeanUtils.copyProperties(studentRequestDto, student);
        return student;
    }

}
