package com.mohress.training.service.audit.action;


import com.mohress.training.dao.TblAuditFlowDao;
import com.mohress.training.dao.TblAuditNodeDao;
import com.mohress.training.dao.TblAuditProjectDao;
import com.mohress.training.dao.TblAuditRecordDao;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.entity.audit.TblAuditNode;
import com.mohress.training.entity.audit.TblAuditProject;
import com.mohress.training.entity.audit.TblAuditRecord;
import com.mohress.training.enums.AuditStatus;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;
import com.mohress.training.util.SpringContextHelper;

import java.util.Date;

/**
 * 审核-回退动作
 * 将步骤退回至上一步骤，即返回至上一处理人处，若为首步骤，则不进行退回；
 *
 */
public class RollBackAction extends AbstractAuditAction {

    private static final int ACTION_ID = 2;

    private String flowId;

    public RollBackAction(String flowId, String auditor, String auditResult) {
        super(auditor, auditResult);
        this.flowId = flowId;
    }

    public TblAuditFlow getAuditFlow() {
        return SpringContextHelper.getBean(TblAuditFlowDao.class).selectByFlowId(flowId);
    }

    protected void doExecute() {
        TblAuditFlow auditFlow = getAuditFlow();

        TblAuditProject auditProject = SpringContextHelper.getBean(TblAuditProjectDao.class).selectByProjectId(auditFlow.getProjectId());

        // 若为首步骤，直接返回
        if (auditFlow.getNodeId() == auditProject.getStartNode()){
            return;
        }

        TblAuditNode auditNode = SpringContextHelper.getBean(TblAuditNodeDao.class).selectByNodeId(auditFlow.getNodeId());

        TblAuditRecord auditRecord = new TblAuditRecord();
        auditRecord.setRecordId("");
        auditRecord.setFlowId(flowId);
        auditRecord.setNodeId(auditFlow.getNodeId());
        auditRecord.setAction(ACTION_ID);
        auditRecord.setAuditor(getAuditor());
        auditRecord.setAuditResult(getAuditResult());
        auditRecord.setCreateTime(new Date());
        auditRecord.setUpdateTime(new Date());

        auditFlow.setNodeId(auditNode.getPreviousNode());
        auditFlow.setNodeStatus(AuditStatus.AUDIT_WAIT.getStatus());

        SpringContextHelper.getBean(TblAuditRecordDao.class).insert(auditRecord);
        int updateResult = SpringContextHelper.getBean(TblAuditFlowDao.class).updateByFlowIdAndVersion(auditFlow.getFlowId(), auditFlow.getVersion());
        if (updateResult != 1){
            throw new BusinessException(ResultCode.AUDIT_FAIL, "更新审核流程信息失败，请重新审核。");
        }
    }
}
