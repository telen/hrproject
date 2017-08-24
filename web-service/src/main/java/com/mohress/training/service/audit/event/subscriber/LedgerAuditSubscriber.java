package com.mohress.training.service.audit.event.subscriber;

import com.google.common.eventbus.Subscribe;
import com.mohress.training.dao.TblLedgerAuditRecordDao;
import com.mohress.training.dao.TblLedgerDao;
import com.mohress.training.entity.TblLedger;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.service.audit.action.InitAction;
import com.mohress.training.service.audit.action.PassAction;
import com.mohress.training.service.audit.action.RejectAction;
import com.mohress.training.service.audit.event.AuditInitEvent;
import com.mohress.training.service.audit.event.AuditPassEvent;
import com.mohress.training.service.audit.event.AuditRejectEvent;
import com.mohress.training.service.audit.event.Subscriber;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 台账审核事件通知
 *
 * Created by youtao.wan on 2017/8/21.
 */
@Slf4j
public class LedgerAuditSubscriber implements Subscriber{

    private static final String AUDIT_TEMPLATE_ID = "";

    @Resource
    private TblLedgerAuditRecordDao ledgerAuditRecordDao;

    @Resource
    private TblLedgerDao ledgerDao;

    /**
     * 台账审核通过
     *
     * @param auditPassEvent 审核通过事件
     */
    @Subscribe
    public void ledgerAuditPass(AuditPassEvent auditPassEvent){
        PassAction passAction = (PassAction)auditPassEvent.getSource();
        TblAuditFlow auditFlow = passAction.getAuditFlow();
        if (!isAccept(auditFlow.getTemplateId())){
            return;
        }

        log.info("台账审核通过通知。recordId={}, ledgerId={}, auditor={}, auditResult={}", auditPassEvent.getRecordId(), auditFlow.getProjectId(), passAction.getAuditor(), passAction.getAuditResult());
    }

    /**
     * 台账审核驳回
     *
     * @param auditRejectEvent 审核驳回事件
     */
    @Subscribe
    public void ledgerAuditReject(AuditRejectEvent auditRejectEvent){
        RejectAction rejectAction = (RejectAction)auditRejectEvent.getSource();
        TblAuditFlow auditFlow = rejectAction.getAuditFlow();
        if (!isAccept(auditFlow.getTemplateId())){
            return;
        }

        log.info("台账审核驳回通知。recordId={}, ledgerId={}, auditor={}, auditResult={}", auditRejectEvent.getRecordId(), auditFlow.getProjectId(), rejectAction.getAuditor(), rejectAction.getAuditResult());
    }

    /**
     * 审核初始化事件
     *
     * @param auditInitEvent 审核初始化事件
     */
    @Subscribe
    public void ledgerAuditInit(AuditInitEvent auditInitEvent){
        InitAction initAction = (InitAction) auditInitEvent.getSource();
        TblAuditFlow auditFlow = initAction.getAuditFlow();
        if (!isAccept(auditFlow.getTemplateId())){
            return;
        }

        log.info("台账审核初始化通知。recordId={}, ledgerId={}, applicant={}", auditInitEvent.getRecordId(), auditFlow.getProjectId(), initAction.getAuditor());

    }

    /**
     * 是否接收该事件
     *
     * @param auditTemplateId 审核模板Id
     * @return 是否接收该事件
     */
    private boolean isAccept(String auditTemplateId){
        return AUDIT_TEMPLATE_ID.equals(auditTemplateId);
    }
}
