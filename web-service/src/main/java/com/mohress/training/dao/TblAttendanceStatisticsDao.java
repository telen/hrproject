package com.mohress.training.dao;

import com.mohress.training.entity.attendance.TblAttendanceStatistics;
import com.mohress.training.service.attendance.AttendanceStatisticsQuery;

import java.util.List;

/**
 * 考勤统计
 * Created by qx.wang on 2017/8/24.
 */
public interface TblAttendanceStatisticsDao {

    int insertSelective(TblAttendanceStatistics statistics);

    List<TblAttendanceStatistics> selectByQuery(AttendanceStatisticsQuery query);

    TblAttendanceStatistics selectByClassId(String classId);

}
