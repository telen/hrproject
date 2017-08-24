package com.mohress.training.service.audit.event.subscriber;

import com.google.common.eventbus.Subscribe;
import com.mohress.training.dao.*;
import com.mohress.training.entity.TblCourse;
import com.mohress.training.entity.TblLedger;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.entity.audit.TblAuditNode;
import com.mohress.training.entity.audit.TblLedgerAuditRecord;
import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.enums.AuditStatus;
import com.mohress.training.service.audit.action.InitAction;
import com.mohress.training.service.audit.action.PassAction;
import com.mohress.training.service.audit.action.RejectAction;
import com.mohress.training.service.audit.event.AuditInitEvent;
import com.mohress.training.service.audit.event.AuditPassEvent;
import com.mohress.training.service.audit.event.AuditRejectEvent;
import com.mohress.training.service.audit.event.Subscriber;
import com.mohress.training.util.constant.AuditConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

/**
 * 台账审核事件通知
 *
 */
@Slf4j
public class LedgerAuditSubscriber implements Subscriber{

    @Resource
    private TblLedgerAuditRecordDao ledgerAuditRecordDao;

    @Resource
    private TblAuditNodeDao auditNodeDao;

    @Resource
    private TblLedgerDao ledgerDao;

    @Resource
    private TblAgencyDao agencyDao;

    @Resource
    private TblCourseDao courseDao;

    @Resource
    private TblClassDao classDao;

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

        TblAuditNode currentAuditNode = auditNodeDao.selectByNodeId(auditFlow.getNodeId());

        if (AuditStatus.AUDIT_WAIT.getStatus() != auditFlow.getFlowStatus()){
            // 审核流程进入终态

            // 1.更新审核人信息
            TblLedgerAuditRecord ledgerAuditRecord = ledgerAuditRecordDao.selectByFlowIdAndRoleId(auditFlow.getFlowId(), currentAuditNode.getAuditRoleId());
            ledgerAuditRecord.setAuditor(passAction.getAuditor());
            ledgerAuditRecord.setAuditResult(passAction.getAuditResult());
            ledgerAuditRecord.setAuditStatus(AuditStatus.AUDIT_PASS.getStatus());
            ledgerAuditRecord.setRecordId(auditPassEvent.getRecordId());

            ledgerAuditRecordDao.updateByPrimaryKey(ledgerAuditRecord);

            // 2.更新台账审核状态为审核通过
            ledgerDao.updateStatusByledgerId(ledgerAuditRecord.getLedgerId(), AuditStatus.AUDIT_PASS.getStatus());
        }else {
            // 审核流程未进入终态

            TblAuditNode previousAuditNode = auditNodeDao.selectByNodeId(currentAuditNode.getPreviousNode());

            // 1.更新审核人信息
            TblLedgerAuditRecord ledgerAuditRecord = ledgerAuditRecordDao.selectByFlowIdAndRoleId(auditFlow.getFlowId(), previousAuditNode.getAuditRoleId());
            ledgerAuditRecord.setAuditor(passAction.getAuditor());
            ledgerAuditRecord.setAuditResult(passAction.getAuditResult());
            ledgerAuditRecord.setAuditStatus(AuditStatus.AUDIT_PASS.getStatus());
            ledgerAuditRecord.setRecordId(auditPassEvent.getRecordId());

            // 2.为下一级审核角色插入一条待审核记录
            TblLedgerAuditRecord waitAuditLedgerAuditRecord = new TblLedgerAuditRecord();
            BeanUtils.copyProperties(ledgerAuditRecord, waitAuditLedgerAuditRecord, "auditor", "auditResult", "recordId");
            waitAuditLedgerAuditRecord.setAuditRoleId(currentAuditNode.getAuditRoleId());
            waitAuditLedgerAuditRecord.setAuditStatus(AuditStatus.AUDIT_WAIT.getStatus());
            waitAuditLedgerAuditRecord.setRecordId("");

            ledgerAuditRecordDao.insert(waitAuditLedgerAuditRecord);
        }
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

        TblAuditNode currentAuditNode = auditNodeDao.selectByNodeId(auditFlow.getNodeId());

        TblLedgerAuditRecord ledgerAuditRecord = ledgerAuditRecordDao.selectByFlowIdAndRoleId(auditFlow.getFlowId(), currentAuditNode.getAuditRoleId());
        ledgerAuditRecord.setAuditor(rejectAction.getAuditor());
        ledgerAuditRecord.setAuditResult(rejectAction.getAuditResult());
        ledgerAuditRecord.setRecordId(auditRejectEvent.getRecordId());
        ledgerAuditRecord.setAuditStatus(AuditStatus.AUDIT_REJECT.getStatus());

        ledgerAuditRecordDao.updateByPrimaryKey(ledgerAuditRecord);
        ledgerDao.updateStatusByledgerId(ledgerAuditRecord.getLedgerId(), AuditStatus.AUDIT_REJECT.getStatus());
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

        TblAuditNode tblAuditNode = auditNodeDao.selectByNodeId(auditFlow.getNodeId());

        TblLedger tblLedger = ledgerDao.selectByLedgerId(auditFlow.getProjectId());

        TblAgency tblAgency = agencyDao.selectByAgencyId(tblLedger.getAgencyId());

        TblCourse tblCourse = courseDao.selectByCourseId(tblLedger.getCourseId());

        TblClass tblClass = classDao.selectByClassId(tblLedger.getClassId());

        // 模糊搜索关键词(培训机构名,培训课程,培训班级)
        String keyWord = AuditConstant.JOINER.join(tblAgency.getAgencyName(), tblCourse.getCourseName(), tblClass.getClassname());

        TblLedgerAuditRecord ledgerAuditRecord = new TblLedgerAuditRecord();

        ledgerAuditRecord.setKeyWord(keyWord);
        ledgerAuditRecord.setFlowId(auditFlow.getFlowId());
        ledgerAuditRecord.setLedgerId(tblLedger.getLedgerId());
        ledgerAuditRecord.setAgencyId(tblLedger.getAgencyId());
        ledgerAuditRecord.setCourseId(tblLedger.getCourseId());
        ledgerAuditRecord.setClassId(tblLedger.getClassId());

        ledgerAuditRecord.setAuditRoleId(tblAuditNode.getAuditRoleId());
        ledgerAuditRecord.setRecordId("");
        ledgerAuditRecord.setAuditor("");
        ledgerAuditRecord.setAuditResult("");
        ledgerAuditRecord.setAuditStatus(AuditStatus.AUDIT_WAIT.getStatus());

        ledgerAuditRecordDao.insert(ledgerAuditRecord);
    }

    /**
     * 是否接收该事件
     *
     * @param auditTemplateId 审核模板Id
     * @return 是否接收该事件
     */
    private boolean isAccept(String auditTemplateId){
        return AuditConstant.LEDGER_AUDIT_TEMPLATE_ID.equals(auditTemplateId);
    }
}
