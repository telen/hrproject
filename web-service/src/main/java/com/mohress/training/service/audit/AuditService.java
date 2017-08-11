package com.mohress.training.service.audit;

import com.mohress.training.exception.ExecuteException;
import com.mohress.training.service.audit.action.AuditAction;

/**
 * 审核服务
 *
 */
public interface AuditService {

    /**
     * 执行审核操作
     *
     * @param action
     * @throws ExecuteException
     */
    void audit(AuditAction action) throws ExecuteException;

}
