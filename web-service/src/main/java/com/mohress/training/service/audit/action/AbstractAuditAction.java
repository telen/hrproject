package com.mohress.training.service.audit.action;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by youtao.wan on 2017/8/13.
 */
@Slf4j
@AllArgsConstructor
public abstract class AbstractAuditAction implements AuditAction{

    private String auditor;

    private String auditResult;

    public void execute() {
        //1.检查审核人是否有权限
        verifyAuthority();
        //2.检查是否符合审核规则
        verifyRule();
        //3.检查审核动作执行是否合法(状态跳转是否正确)
        verifyAction();
        //4.执行审核动作
        doExecute();
    }

    public String getAuditor() {
        return auditor;
    }

    public String getAuditResult() {
        return auditResult;
    }

    private void verifyAuthority(){

    }

    private void verifyRule(){

    }

    private void verifyAction(){

    }

    protected abstract void doExecute();
}
