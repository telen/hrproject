package com.mohress.training.service.audit.impl;

import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.exception.ExecuteException;
import com.mohress.training.service.audit.AuditService;
import com.mohress.training.service.audit.action.AuditAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 审核服务
 *
 */
@Slf4j
@Service
public class AuditServiceImpl implements AuditService{


    public void run(AuditAction action) throws ExecuteException {
        try {
            action.execute();
        }catch (Throwable t){
            TblAuditFlow flow = action.getAuditFlow();
            throw new ExecuteException(flow.toString(), t);
        }
    }
}
