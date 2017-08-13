package com.mohress.training.service.audit.action;


import com.mohress.training.entity.audit.TblAuditFlow;

/**
 * 审核-否决动作
 * 将步骤直接结束，执行结束动作拒绝活动，不再进行操作，或者回退至第一步骤；
 *
 * Created by youtao.wan on 2017/8/10.
 */
public class RejectAction extends AbstractAuditAction {

    public RejectAction(String auditor, String auditResult) {
        super(auditor, auditResult);
    }

    public TblAuditFlow getAuditFlow() {
        return null;
    }

    protected void doExecute() {

    }
}
