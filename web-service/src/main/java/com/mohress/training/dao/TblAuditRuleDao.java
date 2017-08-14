package com.mohress.training.dao;

import com.mohress.training.entity.audit.TblAuditRule;

import java.util.List;

/**
 * Created by youtao.wan on 2017/8/11.
 */
public interface TblAuditRuleDao {

    List<TblAuditRule> selectByNodeId(String nodeId);
}
