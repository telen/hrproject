package com.mohress.training.service.audit.event.subscriber;

import com.google.common.eventbus.Subscribe;
import com.mohress.training.dao.TblAuditNodeDao;
import com.mohress.training.dao.TblAuditRoleDao;
import com.mohress.training.dao.TblClassAuditRecordDao;
import com.mohress.training.dao.TblClassDao;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.entity.audit.TblAuditNode;
import com.mohress.training.entity.audit.TblAuditRole;
import com.mohress.training.entity.audit.TblClassAuditRecord;
import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.enums.AuditStatus;
import com.mohress.training.service.audit.action.PassAction;
import com.mohress.training.service.audit.action.RejectAction;
import com.mohress.training.service.audit.event.AuditPassEvent;
import com.mohress.training.service.audit.event.AuditRejectEvent;
import com.mohress.training.service.audit.event.Subscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

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
    private TblClassAuditRecordDao tblClassAuditRecordDao;

    @Resource
    private TblClassDao tblClassDao;

    @Resource
    private TblAuditNodeDao tblAuditNodeDao;

    @Resource
    private TblAuditRoleDao tblAuditRoleDao;

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

        TblAuditNode currentAuditNode = tblAuditNodeDao.selectByNodeId(auditFlow.getNodeId());

        TblAuditRole currentAuditRole = tblAuditRoleDao.selectByNodeId(currentAuditNode.getNodeId());

        // 审核流程进入终态
        if (AuditStatus.AUDIT_PASS.getStatus() == auditFlow.getFlowStatus()){
            // 1.更新审核人信息
            TblClassAuditRecord classAuditRecord = tblClassAuditRecordDao.selectByClassIdAndAuditRoleId(auditFlow.getFlowId(), currentAuditRole.getRoleId());
            classAuditRecord.setAuditor(passAction.getAuditor());
            classAuditRecord.setAuditResult(passAction.getAuditResult());
            classAuditRecord.setAuditStatus(AuditStatus.AUDIT_PASS.getStatus());
            classAuditRecord.setRecordId(auditPassEvent.getRecordId());

            tblClassAuditRecordDao.updateByClassIdAndAuditRoleId(classAuditRecord);

            // 2.更新班级状态为审核通过
            tblClassDao.updateStatus(auditFlow.getFlowId(), TblClass.Status.ACCESSED);
        }else {
            // 1.更新审核人信息
            TblAuditRole previousAuditRole = tblAuditRoleDao.selectByNodeId(currentAuditNode.getPreviousNode());

            TblClassAuditRecord classAuditRecord = tblClassAuditRecordDao.selectByClassIdAndAuditRoleId(auditFlow.getFlowId(), previousAuditRole.getRoleId());
            classAuditRecord.setAuditor(passAction.getAuditor());
            classAuditRecord.setAuditResult(passAction.getAuditResult());
            classAuditRecord.setAuditStatus(AuditStatus.AUDIT_PASS.getStatus());
            classAuditRecord.setRecordId(auditPassEvent.getRecordId());

            // 2.为下一级审核人添加一条待审核记录
            TblClassAuditRecord waitInsertClassAuditRecord = new TblClassAuditRecord();
            BeanUtils.copyProperties(classAuditRecord, waitInsertClassAuditRecord,  "auditor", "auditResult", "recordId");
            waitInsertClassAuditRecord.setAuditRoleId(currentAuditRole.getRoleId());
            waitInsertClassAuditRecord.setAuditStatus(AuditStatus.AUDIT_WAIT.getStatus());
            waitInsertClassAuditRecord.setRecordId("");
            tblClassAuditRecordDao.insert(waitInsertClassAuditRecord);
        }
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

        TblAuditRole currentAuditRole = tblAuditRoleDao.selectByNodeId(auditFlow.getNodeId());

        // 审核流程进入终态
        TblClassAuditRecord classAuditRecord = tblClassAuditRecordDao.selectByClassIdAndAuditRoleId(auditFlow.getFlowId(), currentAuditRole.getRoleId());
        classAuditRecord.setAuditor(rejectAction.getAuditor());
        classAuditRecord.setAuditResult(rejectAction.getAuditResult());
        classAuditRecord.setAuditStatus(AuditStatus.AUDIT_REJECT.getStatus());
        classAuditRecord.setRecordId(auditRejectEvent.getRecordId());

        tblClassAuditRecordDao.updateByClassIdAndAuditRoleId(classAuditRecord);
        tblClassDao.updateStatus(auditFlow.getFlowId(), TblClass.Status.REJECTED);
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
