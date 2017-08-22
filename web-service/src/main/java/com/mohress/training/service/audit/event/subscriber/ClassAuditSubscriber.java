package com.mohress.training.service.audit.event.subscriber;

import com.google.common.eventbus.Subscribe;
import com.mohress.training.dao.TblClassAuditRecordDao;
import com.mohress.training.dao.TblClassDao;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.service.audit.action.PassAction;
import com.mohress.training.service.audit.action.RejectAction;
import com.mohress.training.service.audit.event.AuditPassEvent;
import com.mohress.training.service.audit.event.AuditRejectEvent;
import com.mohress.training.service.audit.event.Subscriber;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 课程审核事件通知
 *
 * Created by youtao.wan on 2017/8/21.
 */
@Slf4j
public class ClassAuditSubscriber implements Subscriber{

    private static final String AUDIT_TEMPLATE_ID = "";

    @Resource
    private TblClassAuditRecordDao classAuditRecordDao;

    @Resource
    private TblClassDao tblClassDao;

    /**
     * 课程审核通过
     *
     * @param auditPassEvent
     */
    @Subscribe
    public void classAuditPass(AuditPassEvent auditPassEvent){
        PassAction passAction = (PassAction)auditPassEvent.getSource();
        TblAuditFlow auditFlow = passAction.getAuditFlow();
        if (!isAccept(auditFlow.getFlowId())){
            return;
        }

        log.info("课程审核通过通知。recordId={}, classId={}, className={}, auditor={}, auditorResult={}", auditPassEvent.getRecordId(), auditFlow.getFlowId(), "", passAction.getAuditor(), passAction.getAuditResult());
    }

    /**
     * 课程审核不通过
     *
     * @param auditRejectEvent
     */
    @Subscribe
    public void classAuditReject(AuditRejectEvent auditRejectEvent){
        RejectAction rejectAction = (RejectAction)auditRejectEvent.getSource();
        TblAuditFlow auditFlow = rejectAction.getAuditFlow();
        if (!isAccept(auditFlow.getFlowId())){
            return;
        }

        log.info("课程审核否决通知。recordId={}, classId={}, className={}, auditor={}, auditorResult={}。", auditRejectEvent.getRecordId(), auditFlow.getFlowId(), "", rejectAction.getAuditor(), rejectAction.getAuditResult());
    }

    /**
     * 是否接收审核事件
     *
     * @param auditTemplateId
     * @return
     */
    private boolean isAccept(String auditTemplateId){
        return AUDIT_TEMPLATE_ID.equals(auditTemplateId);
    }
}
