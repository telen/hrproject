package com.mohress.training.dao;

import com.mohress.training.entity.audit.TblLedgerAuditRecord;

/**
 * 台账审核记录接口
 *
 */
public interface TblLedgerAuditRecordDao {

    int insert(TblLedgerAuditRecord tblLedgerAuditRecord);
}
