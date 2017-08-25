package com.mohress.training.service.audit.action;

import com.mohress.training.dao.TblAuditFlowDao;
import com.mohress.training.dao.TblAuditLogDao;
import com.mohress.training.dao.TblAuditTemplateDao;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.entity.audit.TblAuditLog;
import com.mohress.training.entity.audit.TblAuditTemplate;
import com.mohress.training.enums.AuditStatus;
import com.mohress.training.service.audit.event.AuditInitEvent;
import com.mohress.training.service.audit.event.EventPublisher;
import com.mohress.training.util.SequenceCreator;
import com.mohress.training.util.SpringContextHelper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 初始化审核流程
 *
 */
public class InitAction extends AbstractAuditAction {

    private static final int ACTION_ID = 0;

    private String auditTemplateId;

    private String auditProjectId;

    private TblAuditFlow auditFlow;

    public InitAction(String auditor, String auditResult, String auditTemplateId, String auditProjectId) {
        super(auditor, auditResult);
        this.auditProjectId = auditProjectId;
        this.auditTemplateId = auditTemplateId;
    }

    public TblAuditFlow getAuditFlow() {
        if (auditFlow == null){
            return initAuditFlow();
        }
        return auditFlow;
    }

    @Transactional
    protected void doExecute() {
        TblAuditFlow tblAuditFlow = getAuditFlow();

        TblAuditLog auditLog = new TblAuditLog();
        auditLog.setAction(ACTION_ID);
        auditLog.setRecordId(SequenceCreator.getAuditRecordId());
        auditLog.setAuditor(getAuditor());
        auditLog.setAuditResult(getAuditResult());
        auditLog.setFlowId(tblAuditFlow.getFlowId());
        auditLog.setNodeId("");
        auditLog.setCreateTime(new Date());
        auditLog.setUpdateTime(new Date());

        SpringContextHelper.getBean(TblAuditFlowDao.class).insert(tblAuditFlow);
        SpringContextHelper.getBean(TblAuditLogDao.class).insert(auditLog);

        // 发布审核流程初始化事件
        AuditInitEvent passEvent = new AuditInitEvent(auditLog.getRecordId(), this);
        SpringContextHelper.getBean(EventPublisher.class).push(passEvent);
    }

    private synchronized TblAuditFlow initAuditFlow(){
        if (auditFlow == null){
            TblAuditTemplateDao tblAuditTemplateDao = SpringContextHelper.getBean(TblAuditTemplateDao.class);

            TblAuditTemplate tblAuditTemplate = tblAuditTemplateDao.selectByTemplateId(auditTemplateId);

            auditFlow = new TblAuditFlow();
            auditFlow.setFlowId(SequenceCreator.getAuditFlowId());
            auditFlow.setTemplateId(auditTemplateId);
            auditFlow.setProjectId(auditProjectId);
            auditFlow.setNodeId(tblAuditTemplate.getStartNode());
            auditFlow.setNodeStatus(AuditStatus.AUDIT_WAIT.getStatus());
            auditFlow.setFlowStatus(AuditStatus.AUDIT_WAIT.getStatus());
            auditFlow.setCreator(getAuditor());
            auditFlow.setCreateTime(new Date());
            auditFlow.setUpdateTime(new Date());
            auditFlow.setVersion(0);
        }
        return auditFlow;
    }
}
