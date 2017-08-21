package com.mohress.training.dao;

import com.mohress.training.entity.audit.TblAuditLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 审核日志记录
 *
 * Created by youtao.wan on 2017/8/11.
 */
public interface TblAuditLogDao {

    int insert(TblAuditLog record);

    List<TblAuditLog> selectByFlowIdAndAuditor(@Param("flowId") String flowId, @Param("auditor") String auditor);
}
