package com.mohress.training.dto.audit;

import lombok.Data;

import java.io.Serializable;

/**
 * 班级审核展示条目
 *
 */
@Data
public class ClassAuditItemDto implements Serializable{

    private String flowId;

    private String agencyName;

    private String className;

    private String applyTime;

    private Integer auditStatus;
}
