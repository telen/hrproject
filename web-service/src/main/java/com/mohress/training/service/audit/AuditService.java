package com.mohress.training.service.audit;

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
     */
    void audit(AuditAction action);

}
