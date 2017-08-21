package com.mohress.training.dto.student;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 学生实体
 * Created by qx.wang on 2017/8/17.
 */
@Data
public class StudentRequestDto implements Serializable {

    private String studentId;

    private String idNumber;

    private String studentName;

    /**
     * 0-男
     * 1-女
     */
    private Integer gender;

    private String nationality;

    private Long birthday;

    /**
     * 身体状态
     */
    private String physicalCondition;

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

    /**
     * 报名所属班级
     */
    private String courseId;

    /**
     * 开户行
     */
    private String bank;

    private String bankAccount;

    private String bankAccountName;

    /**
     * 入学日期
     */
    private Long schoolDate;

    /**
     * 人员类别
     */
    private String studentCat;

    private BigDecimal tuition;
    /**
     * 补贴标准
     */
    private String subsidyCode;
    /**
     * 支付对象
     */
    private String subsidyTarget;
    /**
     * 是否错学
     */
    private Integer isDropout;
    /**
     * 错学日期
     */
    private Long dropout;
    private Integer hasCertificate;
    /**
     * 培训证书类型
     */
    private String certificate;
    /**
     * 培训证书等级
     */
    private String certificateLevel;
    /**
     * 是否用券
     */
    private Integer isUsingCoupon;
    private String couponNum;
    /**
     * 是否企业录用员工
     */
    private Integer isCompact;
    private String compact;
}
