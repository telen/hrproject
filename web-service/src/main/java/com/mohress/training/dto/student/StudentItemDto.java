package com.mohress.training.dto.student;

import lombok.Data;

import java.util.Date;

/**
 * 学生
 * Created by qx.wang on 2017/8/17.
 */
@Data
public class StudentItemDto {

    private String idNumber;

    private String studentName;

    /**
     * 0-男
     * 1-女
     */
    private Integer gender;

    private String nationality;

    private Date birthday;

    private String physicalCondition;

    /**
     * 系统生成ID
     */
    private String studentId;
    /**
     * 婚姻状况
     * 0-已婚
     * 1-未婚
     * 2-离异
     */
    private Integer maritalStatus;

    /**
     * 政治面貌
     */
    private String politicalOutlook;

    /**
     * 是否参保
     * 0-参保
     * 1-未参保
     */
    private Integer insuredStatus;

    private String mobile;

    private String email;

    /**
     * 失业状态
     * 0-待业
     * 1-就业
     */
    private Integer workStatus;

    private String fingerprint;

    private String education;

    private String profession;

    /**
     * 毕业学校
     */
    private String graduationSchool;

    /**
     * 户口所在地
     */
    private String registeredResidence;

    /**
     * 户口性质
     */
    private String registeredProperty;
}
