package com.mohress.training.service.ledger.impl;

import com.mohress.training.dao.*;
import com.mohress.training.dto.ledger.LedgerApplyDto;
import com.mohress.training.entity.security.TblAccount;
import com.mohress.training.service.audit.action.InitAction;
import com.mohress.training.service.ledger.LedgerService;
import com.mohress.training.util.constant.AuditConstant;
import org.springframework.stereotype.Service;

/**
 * 台账服务
 */
@Service
public class LedgerServiceImpl implements LedgerService{

    private TblLedgerDao ledgerDao;

    private TblLedgerStudentDao ledgerStudentDao;

    private TblAccountDao tblAccountDao;

    private TblAgencyDao tblAgencyDao;

    private TblCourseDao tblCourseDao;

    private TblClassDao tblClassDao;

    private TblStudentDao tblStudentDao;


    @Override
    public void apply(LedgerApplyDto ledgerApplyDto) {
        // 1.查询基本信息

        // 2.检查申请人是否与机构匹配
        applicantVerify();

        // 3.检查该班级是否已经申请过
        duplicateApplyVerify();

        // 4.拼装数据

        // 5.保存数据

        // 6.发起审核流程
        InitAction initAction = new InitAction(ledgerApplyDto.getApplicant(), "", AuditConstant.LEDGER_AUDIT_TEMPLATE_ID, "");
        initAction.execute();
    }

    /**
     * 申请人校验
     *
     */
    private void applicantVerify(){

    }

    /**
     * 台账重复申请校验
     */
    private void duplicateApplyVerify(){

    }
}
