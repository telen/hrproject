package com.mohress.training.dao;

import com.mohress.training.entity.audit.TblAuditRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;

/**
 * 审核记录
 *
 * Created by youtao.wan on 2017/8/11.
 */
public interface TblAuditRecordDao {

    int insert(TblAuditRecord record);

    List<TblAuditRecord> selectByFlowIdAndAuditor(@Param("flowId") String flowId, @Param("auditor") String auditor);
}
