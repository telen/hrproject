package com.mohress.training.dao;

import com.mohress.training.entity.audit.TblClassAuditRecord;

/**
 * 培训班级审核记录接口
 *
 * Created by youtao.wan on 2017/8/21.
 */
public interface TblClassAuditRecordDao {

    int insert(TblClassAuditRecord tblClassAuditRecord);

    int updateByClassIdAndAuditRoleId(TblClassAuditRecord tblClassAuditRecord);

    TblClassAuditRecord selectByClassIdAndAuditRoleId(String classId, String auditRoleId);
}
