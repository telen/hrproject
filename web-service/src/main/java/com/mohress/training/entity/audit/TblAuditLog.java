package com.mohress.training.entity.audit;

import lombok.Data;

import java.util.Date;

/**
 * 审核日志
 *
 * Created by youtao.wan on 2017/8/10.
 */
@Data
public class TblAuditLog {

    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 记录Id
     */
    private String recordId;

    /**
     * 流程Id
     */
    private String flowId;

    /**
     * 审批节点
     */
    private String nodeId;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核执行动作
     * 初始化，撤销，回退，通过，否决
     */
    private Integer action;

    /**
     * 审核意见
     */
    private String auditResult;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
