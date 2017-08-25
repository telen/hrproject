package com.mohress.training.dto.audit;

import lombok.Data;

import java.io.Serializable;

/**
 * 台账审核展示条目
 *
 */
@Data
public class LedgerAuditItemDto implements Serializable {

    /**
     * 审核流程Id
     */
    private String flowId;

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
     * 毕业时间
     */
    private String graduateTime;

    /**
     * 出勤率
     */
    private String attendanceRate;

    /**
     * 申请人姓名
     */
    private String applicantName;

    /**
     * 申请人手机号
     */
    private String applicantMobile;

    /**
     * 审核状态
     */
    private Integer auditStatus;
}
