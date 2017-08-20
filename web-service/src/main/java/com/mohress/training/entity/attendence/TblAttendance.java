package com.mohress.training.entity.attendence;

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

    private String deviceId;

    private String deviceName;

    /**
     * 为机器识别出来的id
     */
    private String userId;

    private String remark;

    /**
     * 上课打卡时间
     */
    private Date startTime;

    private Date endTime;

    /**
     * 0-正常
     * 1-请假
     * 2-迟到
     * 3-早退
     */
    private Integer status;

    private Date createTime;
}
