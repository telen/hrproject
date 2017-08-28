package com.mohress.training.service.teacher;

import com.google.common.base.Preconditions;
import com.mohress.training.dto.QueryDto;
import com.mohress.training.dto.teacher.TeacherRequestDto;
import com.mohress.training.entity.TblTeacher;
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
public class TeacherBizImpl implements ModuleBiz {

    @Resource
    private BaseManageService teacherServiceImpl;

    @Override
    public void newModule(String o, String agencyId) {
        Preconditions.checkArgument(o != null);
        TeacherRequestDto teacherRequestDto = null;
        try {
            teacherRequestDto = JsonUtil.getInstance().convertToBean(TeacherRequestDto.class, String.valueOf(o));
        } catch (Exception e) {
            log.error("教师新增反序列化失败 {}", o, e);
        }

        Checker.checkNewTeacher(teacherRequestDto);
        teacherServiceImpl.newModule(buildInsertTblTeacher(teacherRequestDto, agencyId));
    }

    @Override
    public void delete(List<String> ids) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(ids));
        teacherServiceImpl.delete(ids);
    }

    @Override
    public void update(String o) {
        Preconditions.checkArgument(o != null);
        TeacherRequestDto teacherRequestDto = null;
        try {
            teacherRequestDto = JsonUtil.getInstance().convertToBean(TeacherRequestDto.class, String.valueOf(o));
        } catch (Exception e) {
            log.error("教师新增反序列化失败 {}", o, e);
        }
        teacherServiceImpl.update(buildUpdateTblTeacher(teacherRequestDto));
    }

    @Override
    public Object query(QueryDto pageDto) {
        Preconditions.checkNotNull(pageDto);
        Preconditions.checkArgument(pageDto.getPage() >= 0);
        Preconditions.checkArgument(pageDto.getPageSize() > 0);
//        Preconditions.checkArgument(pageDto.getUserId() != null);

        List<TblTeacher> tblTeachers = teacherServiceImpl.query(buildTeacherQuery(pageDto));
        return Convert.convertTeacher(tblTeachers);
    }

    @Override
    public Object queryByKeyword(QueryDto queryDto) {
        Preconditions.checkNotNull(queryDto.getKeyword());

        //关联机构名称
        List<TblTeacher> tblTeachers = teacherServiceImpl.queryByKeyword(buildTeacherQueryByKey(queryDto));
        return Convert.convertTeacher(tblTeachers);
    }

    @Override
    public void checkDelete(String agencyId, List<String> ids) {
        //todo
    }

    private TeacherQuery buildTeacherQueryByKey(QueryDto dto) {
        TeacherQuery query = new TeacherQuery();
        //todo 设置查询者的agency
//        query.setAgencyId();
        query.setKeyword(dto.getKeyword());
        query.setPageIndex(dto.getPage());
        query.setPageSize(dto.getPageSize());
        query.setName(dto.getName());
        return query;
    }

    private TeacherQuery buildTeacherQuery(QueryDto dto) {
        TeacherQuery query = new TeacherQuery();
        //todo 设置查询者的agency
//        query.setAgencyId();
        query.setPageIndex(dto.getPage());
        query.setPageSize(dto.getPageSize());
        query.setName(dto.getName());
        return query;
    }

    private TblTeacher buildInsertTblTeacher(TeacherRequestDto teacherRequestDto, String agencyId) {
        TblTeacher teacher = new TblTeacher();
        BeanUtils.copyProperties(teacherRequestDto, teacher, "birthday");
        teacher.setBirthday(new Date(teacherRequestDto.getBirthday()));
        teacher.setTeacherId(SequenceCreator.getTeacherId());
        teacher.setAgencyId(agencyId);
        return teacher;
    }

    private TblTeacher buildUpdateTblTeacher(TeacherRequestDto teacherRequestDto) {
        TblTeacher teacher = new TblTeacher();
        BeanUtils.copyProperties(teacherRequestDto, teacher);
        teacher.setBirthday(new Date(teacherRequestDto.getBirthday()));
        return teacher;
    }

}
