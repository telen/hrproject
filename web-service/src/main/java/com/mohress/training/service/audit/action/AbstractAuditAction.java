package com.mohress.training.service.audit.action;

import com.mohress.training.dao.TblAuditMemberDao;
import com.mohress.training.dao.TblAuditRuleDao;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.entity.audit.TblAuditMember;
import com.mohress.training.entity.audit.TblAuditRule;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;
import com.mohress.training.util.SpringContextHelper;
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

    public void execute() {
        //1.检查审核人是否有权限
        verifyAuthority();
        //2.检查是否符合审核规则
        verifyRule();
        //3.执行审核动作
        doExecute();
    }

    public String getAuditor() {
        return auditor;
    }

    public String getAuditResult() {
        return auditResult;
    }

    /**
     * 校验审核人是否有该节点下的审核权限
     *
     */
    private void verifyAuthority(){
        TblAuditFlow auditFlow = getAuditFlow();
        TblAuditMember auditMember = SpringContextHelper.getBean(TblAuditMemberDao.class).selectByNodeIdAndUserId(auditFlow.getNodeId(), getAuditor());
        if (auditMember == null){
            log.error("审核权限校验异常。{}在节点{}无审核权限", getAuditor(), auditFlow.getNodeId());
            throw new BusinessException(ResultCode.AUDIT_NO_PRIVILEGE, String.format("%s在节点%s无审核权限", getAuditor(), auditFlow.getNodeId()));
        }
    }

    /**
     * 执行该节点下的审核规则
     *
     */
    private void verifyRule(){
        TblAuditFlow auditFlow = getAuditFlow();
        TblAuditRule auditRule = SpringContextHelper.getBean(TblAuditRuleDao.class).selectByNodeId(auditFlow.getNodeId());
        String rule = auditRule.getRule();

        log.info("执行节点规则 {}", rule);
    }

    /**
     * 模板方法，
     */
    protected abstract void doExecute();
}
