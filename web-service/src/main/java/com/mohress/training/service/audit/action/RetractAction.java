package com.mohress.training.service.audit.action;


import com.mohress.training.entity.audit.TblAuditFlow;

/**
 * 审核-撤回动作
 * 若当前步骤已处理，下一处理人未处理的情况下可进行撤回操作。
 *
 * Created by youtao.wan on 2017/8/10.
 */
public class RetractAction extends AbstractAuditAction {

    public RetractAction(String auditor, String auditResult) {
        super(auditor, auditResult);
    }

    protected void doExecute() {

    }

    public TblAuditFlow getAuditFlow() {
        return null;
    }
}
