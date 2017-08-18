package com.mohress.training.entity;

import lombok.Data;

import java.util.Date;

/**
 * 考勤
 * Created by qx.wang on 2017/8/18.
 */
@Data
public class TblAttendence {

    /**
     * 此处userId为唯一标识用户id，一般为studentId
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
}
