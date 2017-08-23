package com.mohress.training.entity.audit;

import lombok.Data;

/**
 * 课程审核记录
 *
 */
@Data
public class TblClassAuditRecord {

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 记录Id
     */
    private String recordId;

    /**
     * 审核流程Id
     */
    private String flowId;

    /**
     * 班级Id
     */
    private String classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 机构Id
     */
    private String agencyId;

    /**
     * 机构名称
     */
    private String agencyName;

    /**
     * 审核角色Id
     */
    private String auditRoleId;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核结果
     */
    private String auditResult;

    /**
     * 审核状态
     */
    private Integer auditStatus;

}
