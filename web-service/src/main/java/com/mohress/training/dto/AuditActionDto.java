package com.mohress.training.dto;

import lombok.Data;

/**
 * Created by youtao.wan on 2017/8/13.
 */
@Data
public class AuditActionDto {

    private String flowId;

    private String auditor;

    private String auditResult;
}
