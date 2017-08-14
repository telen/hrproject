package com.mohress.training.dao;

import com.mohress.training.entity.audit.TblAuditMember;

import java.util.List;

/**
 * 审核成员
 *
 * Created by youtao.wan on 2017/8/13.
 */
public interface TblAuditMemberDao {

    List<TblAuditMember> selectByNodeId(String nodeId);

    TblAuditMember selectByNodeIdAndUserId(String nodeId, String userId);
}
