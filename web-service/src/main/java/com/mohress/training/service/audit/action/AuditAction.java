package com.mohress.training.service.audit.action;

import com.mohress.training.entity.audit.TblAuditFlow;

/**
 * 审核动作
 *
 */
public interface AuditAction extends Action {

    TblAuditFlow getAuditFlow();
}
