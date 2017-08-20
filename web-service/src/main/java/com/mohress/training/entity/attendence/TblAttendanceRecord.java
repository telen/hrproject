package com.mohress.training.entity.attendence;

import lombok.Data;

import java.util.Date;

/**
 * 考勤
 * Created by qx.wang on 2017/8/18.
 */
@Data
public class TblAttendanceRecord {

    private Integer id;

    private String attendanceId;

    /**
     * 此处userId为唯一标识用户id，是在刷卡识别之后的id
     */
    private String userId;

    /**
     * 打卡时间
     */
    private Date time;

    /**
     * 0-默认
     * 1-补打卡
     */
    private Integer status;

    private Date createTime;
}
