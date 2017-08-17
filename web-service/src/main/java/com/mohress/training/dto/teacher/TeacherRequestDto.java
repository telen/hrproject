package com.mohress.training.dto.teacher;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 教师实体
 * Created by qx.wang on 2017/8/15.
 */
@Data
public class TeacherRequestDto implements Serializable {

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

}
