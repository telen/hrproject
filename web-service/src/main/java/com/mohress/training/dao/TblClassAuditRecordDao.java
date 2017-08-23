package com.mohress.training.dao;

import com.mohress.training.entity.audit.TblClassAuditRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;


/**
 * 培训班级审核记录接口
 *
 * Created by youtao.wan on 2017/8/21.
 */
public interface TblClassAuditRecordDao {

    int insert(TblClassAuditRecord tblClassAuditRecord);

    int updateByFlowIdAndAuditRoleId(TblClassAuditRecord tblClassAuditRecord);

    TblClassAuditRecord selectByFlowIdAndAuditRoleId(@Param("flowId") String flowId, @Param("auditRoleId") String auditRoleId);

    List<TblClassAuditRecord> selectPageByAgencyId(@Param("agencyId") String agencyId, @Param("auditRoleId") String auditRoleId, RowBounds rowBounds);
}
