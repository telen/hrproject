package com.mohress.training.controller;

import com.mohress.training.dto.Response;
import com.mohress.training.dto.ledger.*;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.service.ledger.LedgerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

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
    @RequestMapping("query")
    public Response<List<LedgerItemDto>> queryLedger(Integer pageIndex ,Integer pageSize){
        LedgerQueryDto ledgerQueryDto = new LedgerQueryDto();
        ledgerQueryDto.setPageIndex(pageIndex);
        ledgerQueryDto.setPageSize(pageSize);

        List<LedgerItemDto> ledgerItemDtoList = ledgerService.queryLedger(ledgerQueryDto);

        return new Response<>(ResultCode.SUCCESS.getCode(), "", ledgerItemDtoList);
    }


    /**
     * 查询毕业生台账快照
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("snapshot")
    public Response<List<GraduateSnapshotItemDto>> queryLedgerGraduateSnapshot(@RequestBody GraduateSnapshotQueryDto graduateSnapshotQueryDto){

        List<GraduateSnapshotItemDto> graduateSnapshotItemDtoList = ledgerService.queryLedgerGraduateSnapshot(graduateSnapshotQueryDto);

        return new Response<>(ResultCode.SUCCESS.getCode(), "", graduateSnapshotItemDtoList);
    }
}
