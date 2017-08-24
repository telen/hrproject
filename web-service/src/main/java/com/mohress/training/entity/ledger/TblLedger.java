package com.mohress.training.entity.ledger;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 台账记录
 *
 */
@Data
public class TblLedger {

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 台账Id(唯一键)
     */
    private String ledgerId;

    /**
     * 培训机构Id
     */
    private String agencyId;

    /**
     * 培训课程Id
     */
    private String courseId;

    /**
     * 培训班级Id
     */
    private String classId;

    /**
     * 毕业人数
     */
    private Integer graduateNumbers;

    /**
     * 班级出勤率
     */
    private BigDecimal attendanceRate;

    /**
     * 台账审核状态
     * 0=初始化(默认值)
     * 1=等待审核
     * 2=审核通过
     * 3=审核驳回
     */
    private Integer auditStatus;

    /**
     * 申请人Id
     */
    private String applicant;

    /**
     * 台账申请时间
     */
    private Date createTime;

    /**
     * 台账更新时间
     */
    private Date updateTime;
}
