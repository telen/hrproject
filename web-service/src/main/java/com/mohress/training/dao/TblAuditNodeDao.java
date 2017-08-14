package com.mohress.training.dao;

import com.mohress.training.entity.audit.TblAuditNode;

/**
 * Created by youtao.wan on 2017/8/11.
 */
public interface TblAuditNodeDao {

    TblAuditNode selectByParentNodeId(String parentNodeId);

    TblAuditNode selectByNodeId(String nodeId);
}
