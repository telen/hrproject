package com.mohress.training.service.teacher;

import com.google.common.base.Preconditions;
import com.mohress.training.dto.PageDto;
import com.mohress.training.dto.QueryDto;
import com.mohress.training.dto.teacher.TeacherRequestDto;
import com.mohress.training.entity.TblTeacher;
import com.mohress.training.service.ModuleBiz;
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
public class TeacherBizImpl implements ModuleBiz {

    @Resource
    private TeacherService teacherServiveImpl;

    @Override
    public void newModule(String o) {
        Preconditions.checkArgument(o != null);
        TeacherRequestDto teacherRequestDto = null;
        try {
            teacherRequestDto = JsonUtil.getInstance().convertToBean(TeacherRequestDto.class, String.valueOf(o));
        } catch (Exception e) {
            log.error("教师新增反序列化失败 {}", o, e);
        }
        teacherServiveImpl.newModule(buildInsertTblTeacher(teacherRequestDto));
    }

    @Override
    public void delete(List<String> ids) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(ids));
        teacherServiveImpl.delete(ids);
    }

    @Override
    public void update(String o) {
        Preconditions.checkArgument(o != null);
        TeacherRequestDto teacherRequestDto = null;
        try {
            teacherRequestDto = JsonUtil.getInstance().convertToBean(TeacherRequestDto.class, String.valueOf(0));
        } catch (Exception e) {
            log.error("教师新增反序列化失败 {}", o, e);
        }
        teacherServiveImpl.update(buildUpdateTblTeacher(teacherRequestDto));
    }

    @Override
    public Object query(PageDto pageDto) {
        List<TblTeacher> tblTeachers = teacherServiveImpl.query(buildTeacherQuery(pageDto));
        return Convert.convertTeacher(tblTeachers);
    }

    @Override
    public Object queryByKeyword(QueryDto queryDto) {
        Preconditions.checkNotNull(queryDto.getKeyword());

        //关联机构名称
        List<TblTeacher> tblTeachers = teacherServiveImpl.queryByKeyword(buildTeacherQueryByKey(queryDto));
        return Convert.convertTeacher(tblTeachers);
    }

    private TeacherQuery buildTeacherQueryByKey(QueryDto dto) {
        TeacherQuery query = new TeacherQuery();
        query.setKeyword(dto.getKeyword());
        query.setPageIndex(dto.getPage());
        query.setPageSize(dto.getPageSize());
        return query;
    }

    private TeacherQuery buildTeacherQuery(PageDto dto) {
        TeacherQuery query = new TeacherQuery();
        query.setPageIndex(dto.getPage());
        query.setPageSize(dto.getPageSize());
        return query;
    }

    private TblTeacher buildInsertTblTeacher(TeacherRequestDto teacherRequestDto) {
        TblTeacher teacher = new TblTeacher();
        BeanUtils.copyProperties(teacherRequestDto, teacher);
        teacher.setTeacherId(SequenceCreator.getTeacherId());
        return teacher;
    }

    private TblTeacher buildUpdateTblTeacher(TeacherRequestDto teacherRequestDto) {
        TblTeacher teacher = new TblTeacher();
        BeanUtils.copyProperties(teacherRequestDto, teacher);
        return teacher;
    }

}
