package com.mohress.training.dao;

import com.mohress.training.entity.student.TblExamScore;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 考试成绩数据接口
 *
 */
public interface TblExamScoreDao {

    int insert(TblExamScore tblExamScore);

    int updateByClassIdAndStudentId(TblExamScore tblExamScore);

    List<TblExamScore> selectPageByClassId(String classId, RowBounds rowBounds);

    TblExamScore selectByClassIdAndStudentId(@Param("classId") String classId, @Param("studentId") String studentId);
}
