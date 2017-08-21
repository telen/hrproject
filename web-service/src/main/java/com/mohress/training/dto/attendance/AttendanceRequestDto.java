package com.mohress.training.dto.attendance;

import lombok.Data;

import java.io.Serializable;

/**
 * 考勤
 * Created by qx.wang on 2017/8/20.
 */
@Data
public class AttendanceRequestDto implements Serializable {

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

    private String idNumber;

    /**
     * 为机器识别出来的id
     */
    private String userId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 打卡时间
     */
    private Long attendanceTime;

    private Integer status;

}
