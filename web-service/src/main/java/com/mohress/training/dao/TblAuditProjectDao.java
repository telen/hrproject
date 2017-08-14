package com.mohress.training.dao;

import com.mohress.training.entity.audit.TblAuditProject;

/**
 * Created by youtao.wan on 2017/8/11.
 */
public interface TblAuditProjectDao {

    TblAuditProject selectByProjectId(String projectId);
}
