package com.mohress.training.service.course;

import com.google.common.base.Preconditions;
import com.mohress.training.dto.QueryDto;
import com.mohress.training.dto.course.CourseItemDto;
import com.mohress.training.dto.course.CourseRequestDto;
import com.mohress.training.entity.TblCourse;
import com.mohress.training.entity.TblTeacher;
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
 * 课程服务
 * Created by qx.wang on 2017/8/18.
 */
@Slf4j
@Service
public class CourseBizImpl implements ModuleBiz {

    @Resource
    private BaseManageService courseServiceImpl;

    @Resource
    private TeacherServiceImpl teacherServiceImpl;

    @Override
    public void newModule(String o, String agencyId) {
        Preconditions.checkArgument(o != null);
        CourseRequestDto courseRequestDto;
        try {
            courseRequestDto = JsonUtil.getInstance().convertToBean(CourseRequestDto.class, String.valueOf(o));
        } catch (Exception e) {
            throw new RuntimeException("反序列化课程失败");
        }

        Checker.checkNewCourse(courseRequestDto);
        //todo 校验教师合理性
        courseServiceImpl.newModule(buildInsertTblCourse(courseRequestDto, agencyId));
    }

    @Override
    public void delete(List<String> ids) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(ids));
        courseServiceImpl.delete(ids);
    }

    @Override
    public void update(String o) {
        Preconditions.checkArgument(o != null);
        CourseRequestDto courseRequestDto = null;
        try {
            courseRequestDto = JsonUtil.getInstance().convertToBean(CourseRequestDto.class, String.valueOf(o));
        } catch (Exception e) {
            log.error("教师新增反序列化失败 {}", o, e);
        }
        courseServiceImpl.update(buildUpdateTblCourse(courseRequestDto));
    }

    @Override
    public Object query(QueryDto pageDto) {
        Preconditions.checkNotNull(pageDto);
        Preconditions.checkArgument(pageDto.getPage() >= 0);
        Preconditions.checkArgument(pageDto.getPageSize() > 0);

        List<TblCourse> tblCourses = courseServiceImpl.query(buildCourseQuery(pageDto));

        List<CourseItemDto> courseItemDtos = Convert.convertCourse(tblCourses);
        if (CollectionUtils.isEmpty(courseItemDtos)) {
            return courseItemDtos;
        }
        for (CourseItemDto dto : courseItemDtos) {
            TeacherQuery query = new TeacherQuery();
            query.setTeacherId(dto.getTeacherId());
            List<TblTeacher> teachers = teacherServiceImpl.query(query);
            if (!CollectionUtils.isEmpty(teachers)) {
                dto.setTeacherName(teachers.get(0).getName());
            }
        }
        return courseItemDtos;
    }

    @Override
    public void checkDelete(String agencyId, List<String> ids) {
    }

    private CourseQuery buildCourseQuery(QueryDto dto) {
        CourseQuery query = new CourseQuery();
        query.setAgencyId(dto.getAgencyId());
        query.setPageIndex(dto.getPage());
        query.setPageSize(dto.getPageSize());
        return query;
    }

    private TblCourse buildInsertTblCourse(CourseRequestDto courseRequestDto, String agencyId) {
        TblCourse course = new TblCourse();
        BeanUtils.copyProperties(courseRequestDto, course);
        course.setCourseId(SequenceCreator.getCourseId());
        course.setAgencyId(agencyId);
        return course;
    }

    private TblCourse buildUpdateTblCourse(CourseRequestDto courseRequestDto) {
        TblCourse course = new TblCourse();
        BeanUtils.copyProperties(courseRequestDto, course);
        return course;
    }

}
