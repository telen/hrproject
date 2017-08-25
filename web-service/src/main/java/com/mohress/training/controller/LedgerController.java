package com.mohress.training.controller;

import com.mohress.training.dto.Response;
import com.mohress.training.dto.ledger.LedgerApplyDto;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.service.ledger.LedgerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 台账API接口
 *
 */
@Controller
@RequestMapping("api/ledger/")
public class LedgerController {


    @Resource
    private LedgerService ledgerService;

    /**
     * 培训机构发起台账审核
     *
     * @param ledgerApplyDto
     * @return
     */
    @ResponseBody
    @RequestMapping("apply")
    public Response apply(@RequestBody LedgerApplyDto ledgerApplyDto){

        String userId = "";
        ledgerApplyDto.setApplicant(userId);

        ledgerService.apply(ledgerApplyDto);

        return new Response(ResultCode.SUCCESS.getCode(), "台账申请成功");
    }

    /**
     * 查询台账记录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("queryLedger")
    public Response queryLedger(){
        return null;
    }

    /**
     * 查询台账关联的学生信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("queryLedgerStudent")
    public Response queryLedgerStudent(){
        return null;
    }
}
