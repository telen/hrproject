package com.mohress.training.dao;

import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.service.mclass.ClassQuery;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 班级dao
 * Created by qx.wang on 2017/8/17.
 */
public interface TblClassDao {
    int insertSelective(TblClass tblClass);


    int updateStatus(@Param("classId") String classId, @Param("toStatus") int status);


    int updateSelectiveByClassId(TblClass tblClass);

    /**
     * 限定了机构
     */
    List<TblClass> selectByPage(ClassQuery query);

    List<TblClass> selectByKeyword(ClassQuery query);

    TblClass selectByClassId(String classId);

    List<TblClass> selectByRangeTime(@Param("timeValue") Date date);

    int updateStatusByClassId(TblClass tblClass);
}
