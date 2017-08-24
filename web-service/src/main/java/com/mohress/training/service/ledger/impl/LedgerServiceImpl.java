package com.mohress.training.service.ledger.impl;

import com.google.common.collect.Lists;
import com.mohress.training.dao.*;
import com.mohress.training.dto.ledger.LedgerApplyDto;
import com.mohress.training.entity.agency.TblAccountAgency;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.entity.ledger.TblLedger;
import com.mohress.training.entity.ledger.TblLedgerStudent;
import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.enums.AuditStatus;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;
import com.mohress.training.service.audit.action.InitAction;
import com.mohress.training.service.ledger.LedgerService;
import com.mohress.training.util.constant.AuditConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 台账服务
 */
@Service
public class LedgerServiceImpl implements LedgerService{

    @Resource
    private TblLedgerDao tblLedgerDao;

    @Resource
    private TblLedgerStudentDao tblLedgerStudentDao;

    @Resource
    private TblAgencyDao tblAgencyDao;

    @Resource
    private TblClassDao tblClassDao;

    @Resource
    private TblAccountDao tblAccountDao;

    @Resource
    private TblAccountAgencyDao tblAccountAgencyDao;


    @Transactional
    @Override
    public void apply(LedgerApplyDto ledgerApplyDto) {
        // 1.查询基本信息
        TblAgency tblAgency = tblAgencyDao.selectByAgencyId("");

        TblClass tblClass = tblClassDao.selectByClassId(ledgerApplyDto.getClassId());

        // 2.检查申请人是否与机构匹配
        applicantVerify(ledgerApplyDto, tblAgency);

        // 3.检查该班级是否已经申请过
        duplicateApplyVerify(ledgerApplyDto);

        // 4.拼装数据
        TblLedger tblLedger = packLedgerData();

        List<TblLedgerStudent> ledgerStudentList = packStudentData();

        // 5.保存数据
        tblLedgerDao.insert(tblLedger);
        tblLedgerStudentDao.insertBatch(ledgerStudentList);

        // 6.发起审核流程
        InitAction initAction = new InitAction(ledgerApplyDto.getApplicant(), "", AuditConstant.LEDGER_AUDIT_TEMPLATE_ID, "");
        initAction.execute();
    }

    /**
     * 打包台账数据
     *
     * @return
     */
    private TblLedger packLedgerData(){
        return new TblLedger();
    }

    private List<TblLedgerStudent> packStudentData(){
        return Lists.newArrayList();
    }

    /**
     * 申请人校验
     *
     */
    private void applicantVerify(LedgerApplyDto ledgerApplyDto, TblAgency tblAgency){
        TblAccountAgency tblAccountAgency = tblAccountAgencyDao.selectByUserId(ledgerApplyDto.getApplicant());
        if (tblAccountAgency == null){
            throw new BusinessException(ResultCode.FAIL, "");
        }

        if (tblAccountAgency.getAgencyId() != tblAgency.getAgencyId()){
            throw new BusinessException(ResultCode.FAIL, "");
        }
    }

    /**
     * 台账重复申请校验
     */
    private void duplicateApplyVerify(LedgerApplyDto ledgerApplyDto){
        List<TblLedger> tblLedgerList = tblLedgerDao.selectByClassIdAndStatus(ledgerApplyDto.getClassId(), AuditStatus.AUDIT_WAIT.getStatus());
        if (!CollectionUtils.isEmpty(tblLedgerList)){
            throw new BusinessException(ResultCode.FAIL, "班级台账重复申请");
        }
    }
}
