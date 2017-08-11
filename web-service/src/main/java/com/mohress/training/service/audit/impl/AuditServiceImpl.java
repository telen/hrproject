package com.mohress.training.service.audit.impl;

import com.mohress.training.dao.TblAuditFlowDao;
import com.mohress.training.dao.TblAuditProjectDao;
import com.mohress.training.dao.TblAuditRecordDao;
import com.mohress.training.exception.ExecuteException;
import com.mohress.training.service.audit.AuditService;
import com.mohress.training.service.audit.action.AuditAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by youtao.wan on 2017/8/11.
 */
@Slf4j
@Service
public class AuditServiceImpl implements AuditService{

    @Resource
    private TblAuditProjectDao tblAuditProjectDao;

    @Resource
    private TblAuditRecordDao tblAuditRecordDao;

    @Resource
    private TblAuditFlowDao tblAuditFlowDao;


    public void run(AuditAction action) throws ExecuteException {

    }
}
