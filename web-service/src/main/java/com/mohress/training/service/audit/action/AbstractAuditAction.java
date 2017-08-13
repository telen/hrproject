package com.mohress.training.service.audit.action;

import lombok.extern.slf4j.Slf4j;

/**
 * 抽象审核动作
 *
 * Created by youtao.wan on 2017/8/13.
 */
@Slf4j
public abstract class AbstractAuditAction implements AuditAction{

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核结果
     */
    private String auditResult;

    public AbstractAuditAction(String auditor, String auditResult) {
        this.auditor = auditor;
        this.auditResult = auditResult;
    }

    /**
     * 审核操作
     *
     */
    public void execute() {
        // 执行审核动作
        doExecute();
    }

    public String getAuditor() {
        return auditor;
    }

    public String getAuditResult() {
        return auditResult;
    }



    /**
     * 模板方法，
     */
    protected abstract void doExecute();
}
