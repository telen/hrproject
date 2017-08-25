package com.mohress.training.dao;

import com.mohress.training.entity.audit.TblLedgerAuditRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 台账审核记录接口
 *
 */
public interface TblLedgerAuditRecordDao {

    int insert(TblLedgerAuditRecord tblLedgerAuditRecord);

    TblLedgerAuditRecord selectByFlowIdAndRoleId(@Param("flowId") String flowId, @Param("auditRoleId") String roleId);

    int updateByPrimaryKey(TblLedgerAuditRecord tblLedgerAuditRecord);

    List<TblLedgerAuditRecord> selectPageByRoleId(@Param("agencyId") String agencyId, @Param("auditRoleId") String roleId, RowBounds rowBounds);
}
