package com.mohress.training.service.audit.action;


import com.mohress.training.dao.TblAuditFlowDao;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.util.SpringContextHelper;

/**
 * 审核-撤回动作
 * 若当前步骤已处理，下一处理人未处理的情况下可进行撤回操作。
 *
 */
public class RetractAction extends AbstractAuditAction {

    private static final int ACTION_ID = 1;

    private String flowId;

    public RetractAction(String flowId, String auditor, String auditResult) {
        super(auditor, auditResult);
        this.flowId = flowId;
    }

    protected void doExecute() {

    }

    public TblAuditFlow getAuditFlow() {
        return SpringContextHelper.getBean(TblAuditFlowDao.class).selectByFlowId(flowId);
    }
}
