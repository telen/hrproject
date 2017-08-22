package com.mohress.training.dto.attendance;

import lombok.Data;

import java.io.Serializable;

/**
 * item
 * Created by qx.wang on 2017/8/20.
 */
@Data
public class AttendanceItemDto implements Serializable {

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

    /**
     * 备注
     */
    private String remark;

    private String idNumber;

    /**
     * 上课打卡时间
     */
    private Long startTime;

    private Long endTime;

    /**
     * 0-正常
     * 1-请假
     * 2-迟到
     * 3-早退
     * 4-补打卡
     */
    private Integer status;

}
