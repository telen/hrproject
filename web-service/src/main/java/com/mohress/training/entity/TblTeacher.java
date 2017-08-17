package com.mohress.training.entity;

import lombok.Data;

import java.util.Date;

/**
 * 培训老师
 *
 */
@Data
public class TblTeacher {
    private Integer id;

    /**
     * 教师编号
     */
    private String teacherId;

    /**
     * 机构编号
     */
    private String agencyId;

    /**
     * 身份证号码
     */
    private String idNumber;

    private String name;

    private Integer gender;

    private String nationality;

    private Date birthday;

    /**
     * 住址
     */
    private String address;

    private String mobile;

    private String education;

    /**
     * 0-正常
     * 1-已删除
     */
    private Integer status;

    private Date createTime;

    private Date updateTime;

}
