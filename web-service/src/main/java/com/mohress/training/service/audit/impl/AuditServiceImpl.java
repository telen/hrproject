package com.mohress.training.service.audit.impl;

import com.mohress.training.dao.TblAuditRecordDao;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.entity.audit.TblAuditRecord;
import com.mohress.training.exception.ActionException;
import com.mohress.training.service.audit.AuditService;
import com.mohress.training.service.audit.action.AuditAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 审核服务
 *
 */
@Slf4j
@Service
public class AuditServiceImpl implements AuditService{

    @Resource
    private TblAuditRecordDao auditRecordDao;

    @Transactional
    public void audit(AuditAction action) throws ActionException {
        try {
            // 1.执行审核动作
            action.execute();

            // 2.保存审核记录
            TblAuditRecord auditRecord = newAuditRecord(action);
            auditRecordDao.insert(auditRecord);
        }catch (Throwable t){
            TblAuditFlow flow = action.getAuditFlow();
            log.error("审核流执行异常", t);
            throw new ActionException(flow.toString(), t);
        }
    }

    /**
     *
     * 每个审核操作都必须有所记录
     *
     * @param action 审核动作
     * @return 审核记录
     */
    private TblAuditRecord newAuditRecord(AuditAction action){
        return new TblAuditRecord();
    }
}
