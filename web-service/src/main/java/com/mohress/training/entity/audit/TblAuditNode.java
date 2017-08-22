package com.mohress.training.entity.audit;

import lombok.Data;

/**
 * 审批节点
 *
 * Created by youtao.wan on 2017/8/10.
 */
@Data
public class TblAuditNode {

    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 节点Id
     */
    private String nodeId;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点简单描述
     */
    private String nodeDesc;

    /**
     * 前置节点
     */
    private String previousNode;

    /**
     * 后置节点
     */
    private String nextNode;

    /**
     * 当前节点审核角色Id
     * (神一样的逻辑，*****)
     */
    private String auditRoleId;
}
