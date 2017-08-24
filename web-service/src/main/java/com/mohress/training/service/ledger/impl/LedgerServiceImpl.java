package com.mohress.training.service.ledger.impl;

import com.mohress.training.dao.TblLedgerDao;
import com.mohress.training.dao.TblLedgerStudentDao;
import com.mohress.training.dto.ledger.LedgerApplyDto;
import com.mohress.training.service.audit.action.InitAction;
import com.mohress.training.service.ledger.LedgerService;
import com.mohress.training.util.constant.AuditConstant;

/**
 * 台账服务
 */
public class LedgerServiceImpl implements LedgerService{

    private TblLedgerDao ledgerDao;

    private TblLedgerStudentDao ledgerStudentDao;


    @Override
    public void apply(LedgerApplyDto ledgerApplyDto) {

        // 1.查询基本信息

        // 2.拼装数据

        // 3.保存数据

        // 4.发起审核流程
        InitAction initAction = new InitAction(ledgerApplyDto.getApplicant(), "", AuditConstant.LEDGER_AUDIT_TEMPLATE_ID, "");
        initAction.execute();
    }
}
