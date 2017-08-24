package com.mohress.training.dto.attendance;

import lombok.Data;

import java.io.Serializable;

/**
 * 考勤统计
 * Created by qx.wang on 2017/8/24.
 */
@Data
public class AttendanceStatisticItemDto implements Serializable {

    private String classId;

    private String className;

    private Integer teacherName;

    private String teacherId;

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

}
