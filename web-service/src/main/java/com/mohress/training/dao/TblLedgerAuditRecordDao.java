package com.mohress.training.dao;

import com.mohress.training.entity.audit.TblLedgerAuditRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 台账审核记录接口
 *
 */
public interface TblLedgerAuditRecordDao {

    int insert(TblLedgerAuditRecord tblLedgerAuditRecord);

    TblLedgerAuditRecord selectByFlowIdAndRoleId(@Param("flowId") String flowId, @Param("roleId") String roleId);

    int updateByPrimaryKey(TblLedgerAuditRecord tblLedgerAuditRecord);
}
