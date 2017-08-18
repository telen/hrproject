package com.mohress.training.dao;


import com.mohress.training.entity.TblTeacher;
import com.mohress.training.service.teacher.TeacherQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教师dao
 * Created by qx.wang on 2017/8/16.
 */
public interface TblTeacherDao {
    int insertSelective(TblTeacher tblTeacher);

    int updateByTeacherIdSelective(TblTeacher tblTeacher);

    int updateStatus(@Param("teacherId") String teacherId, @Param("toStatus") int toStatus);

    List<TblTeacher> queryTeacherList(TeacherQuery query);

    List<TblTeacher> queryTeacherByKeyword(TeacherQuery query);
}
