package com.mohress.training.controller;

import com.mohress.training.dto.Response;
import com.mohress.training.dto.ledger.*;
import com.mohress.training.entity.agency.TblAccountAgency;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.service.ledger.LedgerService;
import com.mohress.training.service.security.AccountManager;
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

    @Resource
    private AccountManager accountManager;

    /**
     * 培训机构发起台账审核
     *
     * @param ledgerApplyDto
     * @return
     */
    @ResponseBody
    @RequestMapping("apply")
    public Response apply(@RequestBody LedgerApplyDto ledgerApplyDto){

        String userId = "17081815504021040603";
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
        TblAgency tblAccountAgency = accountManager.queryAgencyByUserId("17081815504021040603");

        LedgerQueryDto ledgerQueryDto = new LedgerQueryDto();
        ledgerQueryDto.setPageIndex(pageIndex);
        ledgerQueryDto.setPageSize(pageSize);
        ledgerQueryDto.setAgencyId(tblAccountAgency.getAgencyId());


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
    public Response<List<GraduateSnapshotItemDto>> queryLedgerGraduateSnapshot(String ledgerId, Integer pageSize, Integer pageIndex){

        GraduateSnapshotQueryDto graduateSnapshotQueryDto = new GraduateSnapshotQueryDto();
        graduateSnapshotQueryDto.setLedgerId(ledgerId);
        graduateSnapshotQueryDto.setPageSize(pageSize);
        graduateSnapshotQueryDto.setPageIndex(pageIndex);

        List<GraduateSnapshotItemDto> graduateSnapshotItemDtoList = ledgerService.queryLedgerGraduateSnapshot(graduateSnapshotQueryDto);

        return new Response<>(ResultCode.SUCCESS.getCode(), "毕业生台账查询成功", graduateSnapshotItemDtoList);
    }
}
