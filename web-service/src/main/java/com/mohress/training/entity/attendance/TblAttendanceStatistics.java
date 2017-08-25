package com.mohress.training.entity.attendance;

import lombok.Data;

import java.util.Date;

/**
 * 考勤统计
 * Created by qx.wang on 2017/8/24.
 */
@Data
public class TblAttendanceStatistics {
    private Integer id;

    private String classId;

    private String classname;

    private String teacherName;

    private String teacherId;

    private String agencyId;

    private String agencyName;

    private Integer totalCount;

    /**
     * 考勤正常人数
     */
    private Integer normalCount;

    /**
     * 迟到
     */
    private Integer beLateCount;

    /**
     * 早退
     */
    private Integer leaveEarlyCount;

    /**
     * 缺勤
     */
    private Integer absentCount;

    /**
     * 补录
     */
    private Integer addedCount;

    private Date createTime;
}
