package com.mohress.training.service.audit.impl;

import com.mohress.training.dao.TblAccountRoleDao;
import com.mohress.training.dao.TblAuditNodeDao;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.entity.audit.TblAuditNode;
import com.mohress.training.entity.security.TblAccountRole;
import com.mohress.training.enums.AuditStatus;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;
import com.mohress.training.service.audit.AuditService;
import com.mohress.training.service.audit.action.AuditAction;
import com.mohress.training.service.audit.action.InitAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 审核服务
 *
 */
@Slf4j
@Service
public class AuditServiceImpl implements AuditService{

    @Resource
    private TblAuditNodeDao auditNodeDao;

    @Resource
    private TblAccountRoleDao accountRoleDao;

    public void audit(AuditAction action) {

        // 1.执行校验
        verify(action);

        // 2.执行审核动作
        action.execute();
    }

    /**
     * 审核校验
     *
     * @param auditAction
     */
    private void verify(AuditAction auditAction){
        TblAuditFlow auditFlow = auditAction.getAuditFlow();

        // 判断审核流程是否存在
        if (auditFlow == null){
            throw new BusinessException(ResultCode.AUDIT_FAIL, "审核流程不存在");
        }

        // 判断审核状态
        if (AuditStatus.AUDIT_WAIT.getStatus() != auditFlow.getFlowStatus()){
            throw new BusinessException(ResultCode.AUDIT_FAIL, "审核流程已进入终态");
        }

        // 审核流程初始化
        if (auditAction instanceof InitAction){
            return;
        }

        List<TblAccountRole> accountRoleList = accountRoleDao.selectByUserId(auditAction.getAuditor());

        TblAuditNode auditNode = auditNodeDao.selectByNodeId(auditFlow.getNodeId());

        // 判断审核人是否拥有当前节点下的审核权限
        for (TblAccountRole it : accountRoleList){
            if (Objects.equals(auditNode.getAuditRoleId(), it.getRoleId())){
                return;
            }
        }

        throw new BusinessException(ResultCode.AUDIT_NO_PRIVILEGE, String.format("%s在%s节点下无审核权限", auditAction.getAuditor(), auditFlow.getNodeId()));
    }



}
