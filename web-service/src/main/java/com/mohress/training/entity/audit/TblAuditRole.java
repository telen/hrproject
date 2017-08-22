package com.mohress.training.entity.audit;

import lombok.Data;

/**
 * 审核角色
 *
 * Created by youtao.wan on 2017/8/13.
 */
@Data
public class TblAuditRole {

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 节点Id
     */
    private String nodeId;

    /**
     * 角色Id
     */
    private String roleId;
}