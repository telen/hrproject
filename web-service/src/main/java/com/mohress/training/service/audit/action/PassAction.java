package com.mohress.training.service.audit.action;

import com.mohress.training.dao.TblAuditFlowDao;
import com.mohress.training.dao.TblAuditNodeDao;
import com.mohress.training.dao.TblAuditLogDao;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.entity.audit.TblAuditNode;
import com.mohress.training.entity.audit.TblAuditLog;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;
import com.mohress.training.service.audit.event.AuditPassEvent;
import com.mohress.training.service.audit.event.EventPublisher;
import com.mohress.training.util.SequenceCreator;
import com.mohress.training.util.SpringContextHelper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.mohress.training.enums.AuditStatus.AUDIT_PASS;
import static com.mohress.training.enums.AuditStatus.AUDIT_WAIT;

/**
 * 审核-通过动作
 * 当前步骤处理通过，进入下一步骤，若为末步骤，则流程处理完成；
 *
 * Created by youtao.wan on 2017/8/10.
 */
public class PassAction extends AbstractAuditAction {

    private static final int ACTION_ID = 1;

    private String flowId;

    public PassAction(String flowId, String auditor, String auditResult) {
        super(auditor, auditResult);
        this.flowId = flowId;
    }

    public TblAuditFlow getAuditFlow() {
        return SpringContextHelper.getBean(TblAuditFlowDao.class).selectByFlowId(flowId);
    }

    @Transactional
    protected void doExecute() {
        TblAuditFlow auditFlow = getAuditFlow();
        TblAuditNode nextAuditNode = SpringContextHelper.getBean(TblAuditNodeDao.class).selectByParentNodeId(auditFlow.getNodeId());

        // 记录存档
        TblAuditLog auditLog = new TblAuditLog();
        auditLog.setAction(ACTION_ID);
        auditLog.setRecordId(SequenceCreator.getAuditRecordId());
        auditLog.setAuditor(getAuditor());
        auditLog.setAuditResult(getAuditResult());
        auditLog.setFlowId(auditFlow.getFlowId());
        auditLog.setNodeId(auditFlow.getNodeId());
        auditLog.setCreateTime(new Date());
        auditLog.setUpdateTime(new Date());

        // 流程指向下一节点
        if (nextAuditNode == null){
            auditFlow.setNodeStatus(AUDIT_PASS.getStatus());
            auditFlow.setFlowStatus(AUDIT_PASS.getStatus());
        }else {
            auditFlow.setNodeId(nextAuditNode.getNodeId());
            auditFlow.setNodeStatus(AUDIT_WAIT.getStatus());
        }

        SpringContextHelper.getBean(TblAuditLogDao.class).insert(auditLog);
        int updateResult = SpringContextHelper.getBean(TblAuditFlowDao.class).updateByFlowIdAndVersion(auditFlow);

        if (updateResult != 1){
            throw new BusinessException(ResultCode.AUDIT_FAIL, "更新审核流程信息失败，请重新审核。");
        }

        // 发布审核通过事件
        AuditPassEvent passEvent = new AuditPassEvent(auditLog.getRecordId(), this);
        SpringContextHelper.getBean(EventPublisher.class).push(passEvent);
    }
}
