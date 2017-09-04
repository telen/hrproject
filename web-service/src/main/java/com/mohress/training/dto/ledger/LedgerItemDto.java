package com.mohress.training.dto.ledger;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by youtao.wan on 2017/8/25.
 */
@Data
public class LedgerItemDto implements Serializable{

    /**
     * 台账唯一标示Id
     */
    private String ledgerId;

    /**
     * 机构Id
     */
    private String agencyId;

    /**
     * 机构名称
     */
    private String agencyName;

    /**
     * 课程Id
     */
    private String courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 班级Id
     */
    private String classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 毕业人数
     */
    private Integer graduateNumbers;

    /**
     * 教师名称
     */
    private String teacherName;

    /**
     * 毕业时间
     */
    private String graduateTime;

    /**
     * 出勤率
     */
    private String attendanceRate;

    /**
     * 台账审核状态
     * 0=初始化(默认值)
     * 1=等待审核
     * 2=审核通过
     * 3=审核驳回
     */
    private Integer auditStatus;

    /**
     * 申请人姓名
     */
    private String applicantName;

    /**
     * 申请人手机号
     */
    private String applicantMobile;
}
