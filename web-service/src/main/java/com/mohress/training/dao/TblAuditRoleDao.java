package com.mohress.training.dao;

import com.mohress.training.entity.audit.TblAuditRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 审核成员
 *
 * Created by youtao.wan on 2017/8/13.
 */
public interface TblAuditRoleDao {

    TblAuditRole selectByNodeId(String nodeId);

    TblAuditRole selectByNodeIdAndRoleId(@Param("nodeId") String nodeId, @Param("roleId") String roleId);
}
