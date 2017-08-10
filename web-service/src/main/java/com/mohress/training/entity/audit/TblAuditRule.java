package com.mohress.training.entity.audit;

import lombok.Data;

/**
 * 审批规则
 *
 * Created by youtao.wan on 2017/8/10.
 */
@Data
public class TblAuditRule {

    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 规则Id
     */
    private String ruleId;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 审核节点Id
     */
    private String nodeId;

    /**
     * 审核规则
     */
    private String rule;

}
