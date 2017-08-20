package com.mohress.training.entity.mclass;

import lombok.Data;

import java.util.Date;

/**
 * 班级学员
 * Created by qx.wang on 2017/8/16.
 */
@Data
public class TblClassMember {
    private Integer id;

    private String classId;

    private String studentId;

    /**
     * 学员在该班级状态，0-默认正常
     */
    private Integer status;

    private Date createTime;

    private Date updateTime;
}
