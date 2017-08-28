package com.mohress.training.entity.attendance;

import lombok.Data;

import java.util.Date;

/**
 * 考勤
 * Created by qx.wang on 2017/8/18.
 */
@Data
public class TblAttendance {

    private Integer id;

    /**
     * 与考勤记录表中一致
     */
    private String attendanceId;

    /**
     * 打卡课程ID
     */
    private String courseId;

    /**
     * 打卡班级ID
     */
    private String classId;

    private String deviceId;

    private String deviceName;

    /**
     * 为机器识别出来的id
     */
    private String userId;

    private String username;

    private String remark;

    /**
     * 打卡时间,将每次打卡时间按照时间戳，通过逗号分隔记录
     */
    private String attendanceTime;

    /**
     * 0-正常
     * 1-请假
     * 2-迟到
     * 3-早退
     * 4-补打卡
     */
    private Integer status;

    private Date createTime;

    private String agencyId;

    public static class Status {
        public static final Integer LEAVE_EARLY = 3;
        public static final Integer BE_LATE = 2;
        public static final Integer LEAVE = 1;
        public static final Integer NORMAL = 0;
        public static final Integer PATCH_CLOCK = 4;
        public static final Integer EXCEPTION = 5;
    }
}
