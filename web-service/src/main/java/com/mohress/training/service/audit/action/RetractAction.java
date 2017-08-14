package com.mohress.training.service.audit.action;


import com.mohress.training.dao.TblAuditFlowDao;
import com.mohress.training.dao.TblAuditRecordDao;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.entity.audit.TblAuditRecord;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;
import com.mohress.training.util.SpringContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 审核-撤回动作
 * 若当前步骤已处理，下一处理人未处理的情况下可进行撤回操作。
 *
 */
@Slf4j
public class RetractAction extends AbstractAuditAction {

    private static final int ACTION_ID = 1;

    private String flowId;

    public RetractAction(String flowId, String auditor, String auditResult) {
        super(auditor, auditResult);
        this.flowId = flowId;
    }

    @Transactional
    protected void doExecute() {
        // 1.获取当前审核流程
        TblAuditFlow auditFlow = getAuditFlow();

        // 2.查询审核人当前流程的审核记录
        TblAuditRecordDao auditRecordDao = SpringContextHelper.getBean(TblAuditRecordDao.class);
        TblAuditRecord auditHistoryRecord = auditRecordDao.selectByFlowIdAndAuditor(flowId, getAuditor());

        // 3.分析审核人执行操作
        switch (auditHistoryRecord.getAction()){
            case 3:
                log.info("审核通过操作回退");
                break;
            case 4:
                log.info("审核否决操作回退");
                break;
            default:
                throw new BusinessException(ResultCode.AUDIT_FAIL, "不支持的回退操作");
        }

        // 4.操作记录入库存档
        TblAuditRecord auditRecord = new TblAuditRecord();
        auditRecord.setAction(ACTION_ID);
        auditRecord.setRecordId("");
        auditRecord.setAuditor(getAuditor());
        auditRecord.setAuditResult(getAuditResult());
        auditRecord.setFlowId(auditFlow.getFlowId());
        auditRecord.setNodeId(auditFlow.getNodeId());
        auditRecord.setCreateTime(new Date());
        auditRecord.setUpdateTime(new Date());

        auditRecordDao.insert(auditRecord);
    }

    public TblAuditFlow getAuditFlow() {
        return SpringContextHelper.getBean(TblAuditFlowDao.class).selectByFlowId(flowId);
    }
}
