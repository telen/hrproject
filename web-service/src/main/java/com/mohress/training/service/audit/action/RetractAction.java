package com.mohress.training.service.audit.action;


import com.mohress.training.dao.TblAuditFlowDao;
import com.mohress.training.dao.TblAuditRecordDao;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.entity.audit.TblAuditRecord;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;
import com.mohress.training.util.DateUtil;
import com.mohress.training.util.SpringContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.mohress.training.enums.AuditStatus.AUDIT_WAIT;

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
        List<TblAuditRecord> auditHistoryRecordList = auditRecordDao.selectByFlowIdAndAuditor(flowId, getAuditor());
        TblAuditRecord auditHistoryRecord = select(auditHistoryRecordList);

        // 3.执行动作5分钟后，拒绝撤销
        if (DateUtil.isBeforeNow(DateUtil.addMinute(auditHistoryRecord.getCreateTime(), 5))){
            throw new BusinessException(ResultCode.AUDIT_FAIL, "超过5分钟撤销时限，不能撤销");
        }

        // 4.分析审核人执行操作
        switch (auditHistoryRecord.getAction()){
            case 3:
                log.info("审核通过操作回退");
                passActionRetract(auditFlow, auditHistoryRecord);
                break;
            case 4:
                log.info("审核否决操作回退");
                rejectActionRetract(auditFlow, auditHistoryRecord);
                break;
            default:
                throw new BusinessException(ResultCode.AUDIT_FAIL, "不支持的撤销操作");
        }

        // 5.操作记录入库存档
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

        int flowUpdateResult = SpringContextHelper.getBean(TblAuditFlowDao.class).updateByFlowIdAndVersion(auditFlow);
        if (flowUpdateResult != 1){
            throw new BusinessException(ResultCode.AUDIT_FAIL, "更新审核流程信息失败，请重新审核");
        }
    }

    public TblAuditFlow getAuditFlow() {
        return SpringContextHelper.getBean(TblAuditFlowDao.class).selectByFlowId(flowId);
    }

    /**
     * 选择用户最近执行的操作动作
     *
     * @param auditHistoryRecordList
     * @return
     */
    private TblAuditRecord select(List<TblAuditRecord> auditHistoryRecordList){
        Collections.sort(auditHistoryRecordList, new Comparator<TblAuditRecord>() {
            public int compare(TblAuditRecord o1, TblAuditRecord o2) {
                return o1.getId() - o2.getId();
            }
        });
        return auditHistoryRecordList.get(0);
    }

    /**
     * 撤销审核通过动作
     *
     * @param auditFlow
     * @param auditRecord
     */
    private void passActionRetract(TblAuditFlow auditFlow, TblAuditRecord auditRecord){
        String currentNodeId = auditFlow.getNodeId();
        String recordNodeId = auditRecord.getNodeId();
        if (currentNodeId.equals(recordNodeId)){
            log.info("当前审核人没变");
            auditFlow.setNodeStatus(AUDIT_WAIT.getStatus());
            auditFlow.setFlowStatus(AUDIT_WAIT.getStatus());
            return;
        }

        if (AUDIT_WAIT.getStatus() != auditFlow.getNodeStatus()){
            throw new BusinessException(ResultCode.AUDIT_FAIL, "下一处理人已处理该流程，无法执行撤回操作。");
        }

        auditFlow.setNodeId(auditRecord.getNodeId());
    }

    /**
     * 撤销审核否决动作
     *
     * @param auditFlow
     * @param auditRecord
     */
    private void rejectActionRetract(TblAuditFlow auditFlow, TblAuditRecord auditRecord){
        auditFlow.setNodeStatus(AUDIT_WAIT.getStatus());
        auditFlow.setFlowStatus(AUDIT_WAIT.getStatus());
    }
}
