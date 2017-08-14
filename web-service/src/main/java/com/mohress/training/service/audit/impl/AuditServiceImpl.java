package com.mohress.training.service.audit.impl;

import com.mohress.training.dao.TblAuditMemberDao;
import com.mohress.training.dao.TblAuditNodeDao;
import com.mohress.training.dao.TblAuditRuleDao;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.entity.audit.TblAuditMember;
import com.mohress.training.entity.audit.TblAuditNode;
import com.mohress.training.entity.audit.TblAuditRule;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;
import com.mohress.training.service.audit.AuditService;
import com.mohress.training.service.audit.action.AuditAction;
import com.mohress.training.service.audit.action.RetractAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 审核服务
 *
 */
@Slf4j
@Service
public class AuditServiceImpl implements AuditService{

    @Resource
    private TblAuditMemberDao auditMemberDao;

    @Resource
    private TblAuditRuleDao auditRuleDao;

    @Resource
    private TblAuditNodeDao auditNodeDao;

    public void audit(AuditAction action) {

        // 1.校验审核权限
        verifyAuthority(action);

        // 2.执行校验规则
        verifyRule(action);

        // 3.执行审核动作
        action.execute();
    }

    /**
     * 校验用户是否具备审核权限
     *
     * @param auditAction
     */
    private void verifyAuthority(AuditAction auditAction){
        TblAuditFlow auditFlow = auditAction.getAuditFlow();
        TblAuditMember auditMember = auditMemberDao.selectByNodeIdAndUserId(auditFlow.getNodeId(), auditAction.getAuditor());

        // 判断审核人是否拥有当前节点下的审核权限
        if (auditMember != null){
            return;
        }

        // 审核人没有当前节点的审核权限，但是执行撤回操作，检查审核人是否具备上一个节点的权限
        if (auditAction instanceof RetractAction){
            TblAuditNode auditNode = auditNodeDao.selectByNodeId(auditFlow.getNodeId());
            TblAuditMember previousAuditMember = auditMemberDao.selectByNodeIdAndUserId(auditNode.getPreviousNode(), auditAction.getAuditor());
            if (previousAuditMember != null){
                return;
            }
        }

        throw new BusinessException(ResultCode.AUDIT_NO_PRIVILEGE, String.format("%s在%s节点下无审核权限", auditAction.getAuditor(), auditFlow.getNodeId()));
    }

    /**
     * 执行该节点下的校验规则
     *
     * @param auditAction
     */
    private void verifyRule(AuditAction auditAction){
        TblAuditFlow auditFlow = auditAction.getAuditFlow();

        List<TblAuditRule> auditRuleList = auditRuleDao.selectByNodeId(auditFlow.getNodeId());
        for (TblAuditRule rule : auditRuleList){
            log.info("执行校验规则 {}", rule);
        }
    }
}
