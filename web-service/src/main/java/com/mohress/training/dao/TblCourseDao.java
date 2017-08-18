package com.mohress.training.dao;

import com.mohress.training.entity.TblCourse;
import com.mohress.training.service.course.CourseQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * Created by qx.wang on 2017/8/18.
 */
public interface TblCourseDao {

    int insertSelective(TblCourse course);

    int updateStatus(@Param("courseId") String courseId, @Param("toStatus") int toStatus);

    int updateByCourseIdSelective(TblCourse course);

    List<TblCourse> queryCourseList(CourseQuery query);

    List<TblCourse> queryCourseByKeyword(CourseQuery query);
}
