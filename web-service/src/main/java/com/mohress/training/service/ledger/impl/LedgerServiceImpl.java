package com.mohress.training.service.ledger.impl;

import com.google.common.collect.Lists;
import com.mohress.training.dao.*;
import com.mohress.training.dto.ledger.*;
import com.mohress.training.entity.agency.TblAccountAgency;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.entity.ledger.TblLedger;
import com.mohress.training.entity.ledger.TblLedgerGraduateSnapshot;
import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.entity.security.TblAccount;
import com.mohress.training.enums.AuditStatus;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;
import com.mohress.training.service.audit.action.InitAction;
import com.mohress.training.service.ledger.LedgerService;
import com.mohress.training.util.DateUtil;
import com.mohress.training.util.constant.AuditConstant;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 台账服务
 */
@Service
public class LedgerServiceImpl implements LedgerService{

    @Resource
    private TblLedgerDao tblLedgerDao;

    @Resource
    private TblLedgerGraduateSnapshotDao tblLedgerGraduateSnapshotDao;

    @Resource
    private TblAgencyDao tblAgencyDao;

    @Resource
    private TblClassDao tblClassDao;

    @Resource
    private TblAccountDao tblAccountDao;

    @Resource
    private TblAccountAgencyDao tblAccountAgencyDao;

    @Override
    public void apply(LedgerApplyDto ledgerApplyDto) {
        // 1.查询基本信息
        TblAgency tblAgency = tblAgencyDao.selectByAgencyId("");

        TblClass tblClass = tblClassDao.selectByClassId(ledgerApplyDto.getClassId());

        // 2.检查申请人是否与机构匹配
        applicantVerify(ledgerApplyDto, tblAgency);

        // 3.检查该班级是否已经申请过
        duplicateApplyVerify(ledgerApplyDto);

        // 4.拼装数据
        TblLedger tblLedger = packLedgerData();
        List<TblLedgerGraduateSnapshot> tblLedgerGraduateSnapshotList = packGraduateSnapshotData();

        // 5.发起审核
        doApply(ledgerApplyDto, tblLedger, tblLedgerGraduateSnapshotList);
    }

    @Override
    public List<LedgerItemDto> queryLedger(LedgerQueryDto ledgerQueryDto) {

        List<TblLedger> tblLedgerList = tblLedgerDao.selectPageByAgencyId(ledgerQueryDto.getAgencyId(), new RowBounds(ledgerQueryDto.getOffset(), ledgerQueryDto.getPageSize()));

        List<LedgerItemDto> ledgerItemDtoList = Lists.newArrayList();
        for (TblLedger it : tblLedgerList){
            LedgerItemDto ledgerItemDto = newLedgerItemDto(it);
            ledgerItemDtoList.add(ledgerItemDto);
        }
        return ledgerItemDtoList;
    }

    @Override
    public List<GraduateSnapshotItemDto> queryLedgerGraduateSnapshot(GraduateSnapshotQueryDto graduateSnapshotQueryDto) {

        List<TblLedgerGraduateSnapshot> tblLedgerGraduateSnapshotList = tblLedgerGraduateSnapshotDao.selectPageByLedgerId(graduateSnapshotQueryDto.getLedgerId(), new RowBounds(graduateSnapshotQueryDto.getOffset(), graduateSnapshotQueryDto.getPageSize()));

        if (CollectionUtils.isEmpty(tblLedgerGraduateSnapshotList)){
            return Lists.newArrayList();
        }

        List<GraduateSnapshotItemDto> list = Lists.newArrayList();

        for (TblLedgerGraduateSnapshot it: tblLedgerGraduateSnapshotList){
            GraduateSnapshotItemDto graduateSnapshotItemDto = newGraduateSnapshotItemDto(it);
            graduateSnapshotItemDto.setLedgerId(graduateSnapshotQueryDto.getLedgerId());
            list.add(graduateSnapshotItemDto);
        }
        return list;
    }

    @Transactional
    private void doApply(LedgerApplyDto ledgerApplyDto, TblLedger tblLedger, List<TblLedgerGraduateSnapshot> ledgerStudentList){
        // 1.保存数据
        tblLedgerDao.insert(tblLedger);
        tblLedgerGraduateSnapshotDao.insertBatch(ledgerStudentList);

        // 2.发起审核流程
        InitAction initAction = new InitAction(ledgerApplyDto.getApplicant(), "", AuditConstant.LEDGER_AUDIT_TEMPLATE_ID, "");
        initAction.execute();
    }

    /**
     * 打包台账数据
     *
     * @return
     */
    private TblLedger packLedgerData(){
        return new TblLedger();
    }

    /**
     * 生成快照数据
     *
     * @return
     */
    private List<TblLedgerGraduateSnapshot> packGraduateSnapshotData(){
        return Lists.newArrayList();
    }

    /**
     * 申请人校验
     *
     */
    private void applicantVerify(LedgerApplyDto ledgerApplyDto, TblAgency tblAgency){
        TblAccountAgency tblAccountAgency = tblAccountAgencyDao.selectByUserId(ledgerApplyDto.getApplicant());
        if (tblAccountAgency == null){
            throw new BusinessException(ResultCode.FAIL, "");
        }

        if (!Objects.equals(tblAccountAgency.getAgencyId() , tblAgency.getAgencyId())){
            throw new BusinessException(ResultCode.FAIL, "");
        }
    }

    /**
     * 台账重复申请校验
     */
    private void duplicateApplyVerify(LedgerApplyDto ledgerApplyDto){
        int count = tblLedgerDao.countByClassIdAndStatus(ledgerApplyDto.getClassId(), AuditStatus.AUDIT_WAIT.getStatus());
        if (count > 0){
            throw new BusinessException(ResultCode.FAIL, "班级台账重复申请");
        }
    }

    private LedgerItemDto newLedgerItemDto(TblLedger tblLedger){
        LedgerItemDto ledgerItemDto = new LedgerItemDto();

        ledgerItemDto.setAgencyId(tblLedger.getLedgerId());
        ledgerItemDto.setAgencyId(tblLedger.getAgencyId());
        ledgerItemDto.setCourseId(tblLedger.getCourseId());
        ledgerItemDto.setClassId(tblLedger.getClassId());
        ledgerItemDto.setGraduateNumbers(tblLedger.getGraduateNumbers());
        ledgerItemDto.setGraduateTime(DateUtil.format(tblLedger.getCreateTime(), "yyyy-MM-dd"));
        ledgerItemDto.setAttendanceRate(tblLedger.getAttendanceRate().toString());

        List<String> list = AuditConstant.SPLITTER.splitToList(tblLedger.getKeyWord());
        ledgerItemDto.setAgencyName(list.get(0));
        ledgerItemDto.setCourseName(list.get(1));
        ledgerItemDto.setClassName(list.get(2));

        TblAccount tblAccount = tblAccountDao.selectByUserId(tblLedger.getApplicant());

        ledgerItemDto.setApplicantName(tblAccount.getUserName());
        ledgerItemDto.setApplicantMobile(tblAccount.getMobile());
        return ledgerItemDto;
    }

    private GraduateSnapshotItemDto newGraduateSnapshotItemDto(TblLedgerGraduateSnapshot tblLedgerGraduateSnapshot){
        GraduateSnapshotItemDto graduateSnapshotItemDto = new GraduateSnapshotItemDto();
        BeanUtils.copyProperties(graduateSnapshotItemDto, tblLedgerGraduateSnapshot, "theoryScore", "practiceScore");

        graduateSnapshotItemDto.setTheoryScore(tblLedgerGraduateSnapshot.getTheoryScore().toString());
        graduateSnapshotItemDto.setPracticeScore(tblLedgerGraduateSnapshot.getPracticeScore().toString());

        return graduateSnapshotItemDto;
    }
}
