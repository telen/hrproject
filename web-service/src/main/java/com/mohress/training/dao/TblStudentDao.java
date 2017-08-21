package com.mohress.training.dao;

import com.mohress.training.entity.TblStudent;
import com.mohress.training.service.student.StudentQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * Created by qx.wang on 2017/8/17.
 */
public interface TblStudentDao {
    int insertSelective(TblStudent student);

    int updateStatus(@Param("studentId") String studentId, @Param("toStatus") int toStatus);

    int updateByStudentIdSelective(TblStudent student);

    List<TblStudent> queryStudentList(StudentQuery query);

}
