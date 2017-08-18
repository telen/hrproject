package com.mohress.training.dao;

import com.mohress.training.entity.TblStudent;
import com.mohress.training.service.student.SCRelation;
import com.mohress.training.service.student.StudentQuery;

import java.util.List;

/**
 * Created by qx.wang on 2017/8/17.
 */
public interface TblStudentDao {
    int insertSelective(TblStudent student);

    int updateStatus(String id, int deleteStatus);

    int updateByStudentIdSelective(TblStudent student);

    List<TblStudent> queryStudentList(StudentQuery m);

    List<TblStudent> queryStudentByKeyword(StudentQuery m);
}
