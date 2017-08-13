package com.mohress.training.service.audit.action;

import com.mohress.training.entity.audit.TblAuditFlow;

/**
 *
 * 审核-新建审核流动作
 *
 */
public class NewFlowAction extends AbstractAuditAction {

    public NewFlowAction(String auditor, String auditResult) {
        super(auditor, auditResult);
    }

    public TblAuditFlow getAuditFlow() {
        return null;
    }

    protected void doExecute() {

    }
}
