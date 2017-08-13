package com.mohress.training.dao;

import com.mohress.training.entity.audit.TblAuditRule;

/**
 * Created by youtao.wan on 2017/8/11.
 */
public interface TblAuditRuleDao {

    TblAuditRule selectByNodeId(String nodeId);
}
