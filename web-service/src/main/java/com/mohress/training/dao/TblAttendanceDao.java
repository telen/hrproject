package com.mohress.training.dao;

import com.mohress.training.entity.attendance.TblAttendance;
import com.mohress.training.service.attendance.AttendanceQuery;

import java.util.List;

/**
 * 考勤
 * Created by qx.wang on 2017/8/20.
 */
public interface TblAttendanceDao {

    int insertSelective(TblAttendance attendance);

    List<TblAttendance> selectByKeyword(AttendanceQuery query);
}
