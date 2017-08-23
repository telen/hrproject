package com.mohress.training.service.audit.event.subscriber;

import com.google.common.eventbus.Subscribe;
import com.mohress.training.dao.*;
import com.mohress.training.entity.TblCourse;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.entity.audit.TblAuditNode;
import com.mohress.training.entity.audit.TblClassAuditRecord;
import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.enums.AuditStatus;
import com.mohress.training.service.audit.action.InitAction;
import com.mohress.training.service.audit.action.PassAction;
import com.mohress.training.service.audit.action.RejectAction;
import com.mohress.training.service.audit.event.AuditInitEvent;
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

    private static final String AUDIT_TEMPLATE_ID = "Class_audit_template";

    @Resource
    private TblClassAuditRecordDao tblClassAuditRecordDao;

    @Resource
    private TblAuditNodeDao tblAuditNodeDao;

    @Resource
    private TblClassDao tblClassDao;

    @Resource
    private TblCourseDao tblCourseDao;

    @Resource
    private TblAgencyDao tblAgencyDao;

    /**
     * 课程审核通过
     *
     * @param auditPassEvent
     */
    @Subscribe
    public void classAuditPass(AuditPassEvent auditPassEvent){
        PassAction passAction = (PassAction)auditPassEvent.getSource();
        TblAuditFlow auditFlow = passAction.getAuditFlow();
        if (!isAccept(auditFlow.getTemplateId())){
            return;
        }

        log.info("课程审核通过通知。recordId={}, classId={}, className={}, auditor={}, auditorResult={}", auditPassEvent.getRecordId(), auditFlow.getFlowId(), "", passAction.getAuditor(), passAction.getAuditResult());

        TblAuditNode currentAuditNode = tblAuditNodeDao.selectByNodeId(auditFlow.getNodeId());

        // 审核流程进入终态
        if (AuditStatus.AUDIT_PASS.getStatus() == auditFlow.getFlowStatus()){
            // 1.更新审核人信息
            TblClassAuditRecord classAuditRecord = tblClassAuditRecordDao.selectByFlowIdAndAuditRoleId(auditFlow.getFlowId(), currentAuditNode.getAuditRoleId());
            classAuditRecord.setAuditor(passAction.getAuditor());
            classAuditRecord.setAuditResult(passAction.getAuditResult());
            classAuditRecord.setAuditStatus(AuditStatus.AUDIT_PASS.getStatus());
            classAuditRecord.setRecordId(auditPassEvent.getRecordId());

            tblClassAuditRecordDao.updateByFlowIdAndAuditRoleId(classAuditRecord);

            // 2.更新班级状态为审核通过
            tblClassDao.updateStatus(auditFlow.getProjectId(), TblClass.Status.ACCESSED);
        }else {
            // 1.更新审核人信息
            TblAuditNode previousAuditNode = tblAuditNodeDao.selectByNodeId(auditFlow.getNodeId());

            TblClassAuditRecord classAuditRecord = tblClassAuditRecordDao.selectByFlowIdAndAuditRoleId(auditFlow.getFlowId(), previousAuditNode.getAuditRoleId());
            classAuditRecord.setAuditor(passAction.getAuditor());
            classAuditRecord.setAuditResult(passAction.getAuditResult());
            classAuditRecord.setAuditStatus(AuditStatus.AUDIT_PASS.getStatus());
            classAuditRecord.setRecordId(auditPassEvent.getRecordId());

            // 2.为下一级审核人添加一条待审核记录
            TblClassAuditRecord waitInsertClassAuditRecord = new TblClassAuditRecord();
            BeanUtils.copyProperties(classAuditRecord, waitInsertClassAuditRecord,  "auditor", "auditResult", "recordId");
            waitInsertClassAuditRecord.setAuditRoleId(currentAuditNode.getAuditRoleId());
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
        if (!isAccept(auditFlow.getTemplateId())){
            return;
        }

        log.info("课程审核否决通知。recordId={}, classId={}, className={}, auditor={}, auditorResult={}。", auditRejectEvent.getRecordId(), auditFlow.getFlowId(), "", rejectAction.getAuditor(), rejectAction.getAuditResult());

        TblAuditNode currentAuditNode = tblAuditNodeDao.selectByNodeId(auditFlow.getNodeId());

        // 审核流程进入终态
        TblClassAuditRecord classAuditRecord = tblClassAuditRecordDao.selectByFlowIdAndAuditRoleId(auditFlow.getFlowId(), currentAuditNode.getAuditRoleId());
        classAuditRecord.setAuditor(rejectAction.getAuditor());
        classAuditRecord.setAuditResult(rejectAction.getAuditResult());
        classAuditRecord.setAuditStatus(AuditStatus.AUDIT_REJECT.getStatus());
        classAuditRecord.setRecordId(auditRejectEvent.getRecordId());

        tblClassAuditRecordDao.updateByFlowIdAndAuditRoleId(classAuditRecord);
        tblClassDao.updateStatus(auditFlow.getProjectId(), TblClass.Status.REJECTED);
    }

    /**
     * 班级审核流程初始化事件
     *
     * @param auditInitEvent
     */
    @Subscribe
    public void classAuditInit(AuditInitEvent auditInitEvent){
        InitAction initAction = (InitAction)auditInitEvent.getSource();
        TblAuditFlow auditFlow = initAction.getAuditFlow();
        if (!isAccept(auditFlow.getTemplateId())){
            return;
        }

        log.info("课程审核流程初始化通知。recordId={}, classId={}, className={}, creator={}", auditInitEvent.getRecordId(), auditFlow.getFlowId(), "", initAction.getAuditor());

        TblAuditNode tblAuditNode = tblAuditNodeDao.selectByNodeId(auditFlow.getNodeId());

        TblClass tblClass = tblClassDao.selectByClassId(auditFlow.getProjectId());

        TblCourse tblCourse = tblCourseDao.selectByCourseId(tblClass.getCourseId());

        TblAgency tblAgency = tblAgencyDao.selectByAgencyId(tblCourse.getAgencyId());

        TblClassAuditRecord classWaitAuditRecord = new TblClassAuditRecord();
        classWaitAuditRecord.setFlowId(auditFlow.getFlowId());
        classWaitAuditRecord.setClassId(tblClass.getClassId());
        classWaitAuditRecord.setClassName(tblClass.getClassname());
        classWaitAuditRecord.setAgencyId(tblAgency.getAgencyId());
        classWaitAuditRecord.setAgencyName(tblAgency.getAgencyName());
        classWaitAuditRecord.setAuditStatus(AuditStatus.AUDIT_WAIT.getStatus());
        classWaitAuditRecord.setAuditRoleId(tblAuditNode.getAuditRoleId());
        classWaitAuditRecord.setApplyTime(auditFlow.getCreateTime());
        classWaitAuditRecord.setAuditor("");
        classWaitAuditRecord.setAuditResult("");
        classWaitAuditRecord.setRecordId("");

        tblClassAuditRecordDao.insert(classWaitAuditRecord);
        tblClassDao.updateStatus(auditFlow.getProjectId(), TblClass.Status.APPLIED);
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
