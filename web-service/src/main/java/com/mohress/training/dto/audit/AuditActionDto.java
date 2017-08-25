package com.mohress.training.dto.audit;

import lombok.Data;

/**
 * 接收前端审核操作参数
 *
 * Created by youtao.wan on 2017/8/13.
 */
@Data
public class AuditActionDto {

    private String flowId;

    private String auditor;

    private String auditResult;
}
