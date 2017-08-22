package com.mohress.training.dao;

import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.service.mclass.ClassQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 班级dao
 * Created by qx.wang on 2017/8/17.
 */
public interface TblClassDao {
    int insertSelective(TblClass tblClass);


    int updateStatus(@Param("classId") String classId, @Param("status") int status);


    int updateSelectiveByClassId(TblClass tblClass);

    /**
     * 限定了机构
     */
    List<TblClass> selectByPage(ClassQuery query);

    List<TblClass> selectByKeyword(ClassQuery query);

    int updateByClassId(@Param("classId") String classId, @Param("beforeStatus") int beforeStatus, @Param("toStatus") int toStatus);

    TblClass selectByClassId(String classId);
}
