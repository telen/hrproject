package com.mohress.training.service.audit.action;

import com.mohress.training.entity.audit.TblAuditFlow;

/**
 * 审核动作
 *
 */
public interface AuditAction extends Action {

    /**
     * 审核人
     *
     * @return
     */
    String getAuditor();

    /**
     * 审核结果
     *
     * @return
     */
    String getAuditResult();

    /**
     * 审核流
     *
     * @return
     */
    TblAuditFlow getAuditFlow();
}
