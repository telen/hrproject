package com.mohress.training.dao;

import com.mohress.training.entity.attendance.TblAttendance;
import com.mohress.training.service.attendance.AttendanceQuery;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 考勤
 * Created by qx.wang on 2017/8/20.
 */
public interface TblAttendanceDao {

    int insertSelective(TblAttendance attendance);

    List<TblAttendance> selectByKeyword(AttendanceQuery query);

    TblAttendance selectByDate(@Param("userId") String userId, @Param("todayStart") Date todayStart, @Param("todayEnd") Date todayEnd);

    int updateByPrimaryKeySelective(TblAttendance attendance);
}
