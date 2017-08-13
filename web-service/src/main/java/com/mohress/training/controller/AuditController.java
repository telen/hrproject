package com.mohress.training.controller;

import com.mohress.training.dto.AuditActionDto;
import com.mohress.training.dto.Response;
import com.mohress.training.service.audit.AuditService;
import com.mohress.training.service.audit.action.PassAction;
import com.mohress.training.service.audit.action.RejectAction;
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

    @ResponseBody
    @RequestMapping("pass")
    public Response auditPass(@RequestBody AuditActionDto auditActionDto){
        PassAction passAction = new PassAction(auditActionDto.getFlowId(), auditActionDto.getAuditor(), auditActionDto.getAuditResult());

        auditService.audit(passAction);

        Response response = new Response();
        response.setCode(AUDIT_SUCCESS.getCode());
        response.setMessage(AUDIT_SUCCESS.getDesc());
        return response;
    }

    @ResponseBody
    @RequestMapping("reject")
    public Response auditReject(@RequestBody AuditActionDto auditActionDto){
        RejectAction rejectAction = new RejectAction(auditActionDto.getFlowId(), auditActionDto.getAuditor(), auditActionDto.getAuditResult());

        auditService.audit(rejectAction);

        Response response = new Response();
        response.setCode(AUDIT_SUCCESS.getCode());
        response.setMessage(AUDIT_SUCCESS.getDesc());
        return response;
    }

    @ResponseBody
    @RequestMapping("retract")
    public Response auditRetract(@RequestBody AuditActionDto auditActionDto){
        return new Response();
    }

    @ResponseBody
    @RequestMapping("rollBack")
    public Response auditRollback(@RequestBody AuditActionDto auditActionDto){
        return new Response();
    }
}
