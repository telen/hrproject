package com.mohress.training.entity;

import lombok.Data;

import java.util.Date;

/**
 * 记录任务执行时间
 * Created by qx.wang on 2017/8/24.
 */
@Data
public class TblJobTime {
    private Integer id;

    private String jobName;

    private Date timeValue;

    private Date createTime;

    private Date updateTime;

}
