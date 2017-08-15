package com.mohress.training.controller;

import com.mohress.training.dto.AuditActionDto;
import com.mohress.training.dto.Response;
import com.mohress.training.service.audit.AuditService;
import com.mohress.training.service.audit.action.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import static com.mohress.training.enums.ResultCode.AUDIT_SUCCESS;

/**
 * 审核接口
 *
 */
@Controller
@RequestMapping("api/audit/")
public class AuditController {

    @Resource
    private AuditService auditService;

    /**
     * 审核通过
     * 当前步骤处理通过，进入下一步骤，若为末步骤，则流程处理完成；
     *
     * @param auditActionDto
     * @return
     */
    @ResponseBody
    @RequestMapping("pass")
    public Response auditPass(@RequestBody AuditActionDto auditActionDto){
        PassAction passAction = new PassAction(auditActionDto.getFlowId(), auditActionDto.getAuditor(), auditActionDto.getAuditResult());

        return audit(passAction);
    }

    /**
     * 审核否决
     * 将步骤直接结束，执行结束动作拒绝活动，不再进行操作，或者回退至第一步骤；
     *
     * @param auditActionDto
     * @return
     */
    @ResponseBody
    @RequestMapping("reject")
    public Response auditReject(@RequestBody AuditActionDto auditActionDto){
        RejectAction rejectAction = new RejectAction(auditActionDto.getFlowId(), auditActionDto.getAuditor(), auditActionDto.getAuditResult());

        return audit(rejectAction);
    }

    /**
     * 撤回操作
     * 若当前步骤已处理，下一处理人未处理的情况下可进行撤回操作。
     *
     * @param auditActionDto
     * @return
     */
    @ResponseBody
    @RequestMapping("retract")
    public Response auditRetract(@RequestBody AuditActionDto auditActionDto){
        RetractAction retractAction = new RetractAction(auditActionDto.getFlowId(), auditActionDto.getAuditor(), auditActionDto.getAuditResult());

        return audit(retractAction);
    }

    /**
     * 回退操作
     * 将步骤退回至上一步骤，即返回至上一处理人处，若为首步骤，则不进行退回；
     *
     * @param auditActionDto
     * @return
     */
    @ResponseBody
    @RequestMapping("rollBack")
    public Response auditRollback(@RequestBody AuditActionDto auditActionDto){
        RollBackAction rollBackAction = new RollBackAction(auditActionDto.getFlowId(), auditActionDto.getAuditor(), auditActionDto.getAuditResult());

        return audit(rollBackAction);
    }


    private Response audit(AuditAction auditAction){

        auditService.audit(auditAction);

        return new Response(AUDIT_SUCCESS.getCode(), AUDIT_SUCCESS.getDesc());
    }
}
