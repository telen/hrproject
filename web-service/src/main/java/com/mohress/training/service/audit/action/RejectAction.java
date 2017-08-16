package com.mohress.training.service.audit.action;


import com.mohress.training.dao.TblAuditFlowDao;
import com.mohress.training.dao.TblAuditRecordDao;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.entity.audit.TblAuditRecord;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;
import com.mohress.training.util.SpringContextHelper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.mohress.training.enums.AuditStatus.AUDIT_REJECT;

/**
 * 审核-否决动作
 * 将步骤直接结束，执行结束动作拒绝活动，不再进行操作，或者回退至第一步骤；
 *
 * Created by youtao.wan on 2017/8/10.
 */
public class RejectAction extends AbstractAuditAction {

    private static final int ACTION_ID = 4;

    private String flowId;

    public RejectAction(String flowId, String auditor, String auditResult) {
        super(auditor, auditResult);
        this.flowId = flowId;
    }

    public TblAuditFlow getAuditFlow() {
        return SpringContextHelper.getBean(TblAuditFlowDao.class).selectByFlowId(flowId);
    }

    @Transactional
    protected void doExecute() {
        TblAuditFlow auditFlow = getAuditFlow();

        auditFlow.setNodeStatus(AUDIT_REJECT.getStatus());
        auditFlow.setFlowStatus(AUDIT_REJECT.getStatus());

        TblAuditRecord auditRecord = new TblAuditRecord();
        auditRecord.setAction(ACTION_ID);
        auditRecord.setRecordId("");
        auditRecord.setAuditor(getAuditor());
        auditRecord.setAuditResult(getAuditResult());
        auditRecord.setFlowId(auditFlow.getFlowId());
        auditRecord.setNodeId(auditFlow.getNodeId());
        auditRecord.setCreateTime(new Date());
        auditRecord.setUpdateTime(new Date());

        SpringContextHelper.getBean(TblAuditRecordDao.class).insert(auditRecord);
        int updateResult = SpringContextHelper.getBean(TblAuditFlowDao.class).updateByFlowIdAndVersion(auditFlow);

        if (updateResult != 1){
            throw new BusinessException(ResultCode.AUDIT_FAIL, "更新审核流程信息失败，请重新审核。");
        }
    }
}
