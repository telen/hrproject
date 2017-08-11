package com.mohress.training.service.audit;

import com.mohress.training.exception.ExecuteException;
import com.mohress.training.service.audit.action.AuditAction;

/**
 * 审核服务
 *
 */
public interface AuditService {

    void run(AuditAction action) throws ExecuteException;

}
