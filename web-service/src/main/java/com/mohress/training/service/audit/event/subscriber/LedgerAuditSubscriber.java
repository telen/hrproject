package com.mohress.training.service.audit.event.subscriber;

import com.google.common.eventbus.Subscribe;
import com.mohress.training.dao.TblLedgerAuditRecordDao;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.service.audit.action.PassAction;
import com.mohress.training.service.audit.action.RejectAction;
import com.mohress.training.service.audit.event.AuditPassEvent;
import com.mohress.training.service.audit.event.AuditRejectEvent;
import com.mohress.training.service.audit.event.Subscriber;
import lombok.extern.slf4j.Slf4j;

/**
 * 台账审核事件通知
 *
 * Created by youtao.wan on 2017/8/21.
 */
@Slf4j
public class LedgerAuditSubscriber implements Subscriber{

    private static final String AUDIT_TEMPLATE_ID = "";

    private TblLedgerAuditRecordDao ledgerAuditRecordDao;

    /**
     * 台账审核通过
     *
     * @param auditPassEvent
     */
    @Subscribe
    public void ledgerAuditPass(AuditPassEvent auditPassEvent){
        PassAction passAction = (PassAction)auditPassEvent.getSource();
        TblAuditFlow auditFlow = passAction.getAuditFlow();
        if (!isAccept(auditFlow.getFlowId())){
            return;
        }

        log.info("台账审核通过通知。recordId={}, ledgerId={}, ledgerName={}, auditor={}, auditResult={}", auditPassEvent.getRecordId(), "", "", passAction.getAuditor(), passAction.getAuditResult());
    }

    /**
     * 台账审核不通过
     *
     * @param auditRejectEvent
     */
    @Subscribe
    public void ledgerAuditReject(AuditRejectEvent auditRejectEvent){
        RejectAction rejectAction = (RejectAction)auditRejectEvent.getSource();
        TblAuditFlow auditFlow = rejectAction.getAuditFlow();
        if (!isAccept(auditFlow.getFlowId())){
            return;
        }

        log.info("台账审核否决通知。recordId={}, ledgerId={}, ledgerName={}, auditor={}, auditResult={}", auditRejectEvent.getRecordId(), "", "", rejectAction.getAuditor(), rejectAction.getAuditResult());
    }

    /**
     * 是否接收该事件
     *
     * @param auditTemplateId
     * @return
     */
    private boolean isAccept(String auditTemplateId){
        return AUDIT_TEMPLATE_ID.equals(auditTemplateId);
    }
}
