package com.mohress.training.entity.audit;

import lombok.Data;

/**
 * 审核成员
 *
 * Created by youtao.wan on 2017/8/13.
 */
@Data
public class TblAuditMember {

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 节点Id
     */
    private String nodeId;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 审核成员优先级
     */
    private Integer priority;
}