package com.mohress.training.service.audit.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mohress.training.dao.TblAccountDao;
import com.mohress.training.dao.TblAuditFlowDao;
import com.mohress.training.dao.TblLedgerAuditRecordDao;
import com.mohress.training.dao.TblLedgerDao;
import com.mohress.training.dto.audit.LedgerAuditItemDto;
import com.mohress.training.dto.audit.LedgerAuditQueryDto;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.entity.audit.TblLedgerAuditRecord;
import com.mohress.training.entity.ledger.TblLedger;
import com.mohress.training.entity.security.TblAccount;
import com.mohress.training.entity.security.TblAccountRole;
import com.mohress.training.service.BaseQuery;
import com.mohress.training.service.audit.AuditRecordService;
import com.mohress.training.service.security.AccountManager;
import com.mohress.training.util.AccountAuthority;
import com.mohress.training.util.DateUtil;
import com.mohress.training.util.constant.AuditConstant;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by youtao.wan on 2017/8/25.
 */
@Service
public class LedgerAuditRecordServiceImpl implements AuditRecordService<LedgerAuditItemDto> {

    @Resource
    private TblLedgerAuditRecordDao ledgerAuditRecordDao;

    @Resource
    private TblAccountDao tblAccountDao;

    @Resource
    private TblAuditFlowDao tblAuditFlowDao;

    @Resource
    private TblLedgerDao tblLedgerDao;

    @Resource
    private AccountManager accountManager;

    @Override
    public List<LedgerAuditItemDto> queryByPage(BaseQuery baseQuery) {
        LedgerAuditQueryDto ledgerAuditQueryDto = (LedgerAuditQueryDto) baseQuery;

        AccountAuthority accountAuthority = accountManager.queryAccountAuthorityByUserId(ledgerAuditQueryDto.getUserId());
        ledgerAuditQueryDto.setAuditRoleId(accountAuthority.getAuthorityList().get(0).getRole().getRoleId());

        List<TblLedgerAuditRecord> ledgerAuditRecordList = ledgerAuditRecordDao.selectPageByRoleId(ledgerAuditQueryDto.getAgencyId(), ledgerAuditQueryDto.getAuditRoleId(), new RowBounds(ledgerAuditQueryDto.getOffset(), ledgerAuditQueryDto.getPageSize()));

        List<LedgerAuditItemDto> ledgerAuditItemDtoList = Lists.newArrayList();

        for (TblLedgerAuditRecord it : ledgerAuditRecordList){
            LedgerAuditItemDto ledgerAuditItemDto = new LedgerAuditItemDto();
            BeanUtils.copyProperties(it, ledgerAuditItemDto);

            TblAuditFlow tblAuditFlow = tblAuditFlowDao.selectByFlowId(it.getFlowId());
            TblAccount tblAccount = tblAccountDao.selectByUserId(tblAuditFlow.getCreator());
            TblLedger tblLedger = tblLedgerDao.selectByLedgerId(it.getLedgerId());

            List<String> list = AuditConstant.SPLITTER.splitToList(tblLedger.getKeyWord());

            ledgerAuditItemDto.setAgencyName(list.get(0));
            ledgerAuditItemDto.setCourseName(list.get(1));
            ledgerAuditItemDto.setClassName(list.get(2));

            ledgerAuditItemDto.setGraduateNumbers(tblLedger.getGraduateNumbers());
            ledgerAuditItemDto.setGraduateTime(DateUtil.format(tblLedger.getCreateTime(), "yyyy-MM-dd"));
            ledgerAuditItemDto.setAttendanceRate(tblLedger.getAttendanceRate().toString());
            ledgerAuditItemDto.setApplicantName(tblAccount.getUserName());
            ledgerAuditItemDto.setApplicantMobile(tblAccount.getMobile());

            ledgerAuditItemDtoList.add(ledgerAuditItemDto);
        }

        return ledgerAuditItemDtoList;
    }
}
