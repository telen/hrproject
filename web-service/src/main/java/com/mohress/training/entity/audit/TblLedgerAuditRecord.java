package com.mohress.training.entity.audit;

import lombok.Data;

/**
 * 台账审核记录
 */
@Data
public class TblLedgerAuditRecord {

    /**
     * 主键Id
     */
    private Long Id;

    /**
     * 记录Id
     * 与审核日志的recordId保持一致
     */
    private String recordId;

    /**
     * 审核流程Id
     */
    private String flowId;

    /**
     * 台账记录Id
     */
    private String ledgerId;

    /**
     * 机构Id
     */
    private String agencyId;

    /**
     * 课程Id
     */
    private String courseId;

    /**
     * 班级Id
     */
    private String classId;

    /**
     * 记录搜索关键字
     * 格式定义：
     * 培训机构名,培训课程,培训班级
     * 示例：
     * 培训机构-A,护士培训,护士培训第一期
     */
    private String keyWord;

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
