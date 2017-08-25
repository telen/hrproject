package com.mohress.training.service.mclass;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mohress.training.dao.TblClassDao;
import com.mohress.training.dao.TblClassMemberDao;
import com.mohress.training.dao.TblExamScoreDao;
import com.mohress.training.dao.TblStudentDao;
import com.mohress.training.dto.mclass.ClassApplyDto;
import com.mohress.training.dto.mclass.ClassGraduateDto;
import com.mohress.training.dto.student.GraduateItemDto;
import com.mohress.training.dto.student.GraduateQueryDto;
import com.mohress.training.dto.student.GraduateDto;
import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.entity.mclass.TblClassMember;
import com.mohress.training.entity.student.TblExamScore;
import com.mohress.training.entity.student.TblStudent;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;
import com.mohress.training.service.BaseManageService;
import com.mohress.training.service.audit.action.InitAction;
import com.mohress.training.util.BusiVerify;
import com.mohress.training.util.Convert;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 班级管理
 * Created by qx.wang on 2017/8/17.
 */
@Slf4j
@Service
public class ClassServiceImpl implements BaseManageService {
    private static final int DELETE_STATUS = 1;

    @Resource
    private TblClassDao tblClassDao;

    @Resource
    private TblClassMemberDao tblClassMemberDao;

    @Resource
    private TblExamScoreDao tblExamScoreDao;

    @Resource
    private TblStudentDao tblStudentDao;

    @Override
    public <T> void newModule(T t) {
        BusiVerify.verify(tblClassDao.insertSelective(((ClassStudent) t).getTblClass()) > 0, "新增班级SQL异常");
        List<TblClassMember> tblClassMembers = ((ClassStudent) t).getTblClassMembers();
        if (!CollectionUtils.isEmpty(tblClassMembers)) {
            BusiVerify.verify(tblClassMemberDao.insertBatchSelective(tblClassMembers) > 0, "新增班级SQL异常");
        }
    }

    @Override
    @Transactional
    public void delete(List<String> ids) {
        for (String id : ids) {
            BusiVerify.verify(tblClassDao.updateStatus(id, DELETE_STATUS) > 0, "更新班级删除状态SQL失败");
        }
    }

    @Override
    public <T> void update(T t) {
        BusiVerify.verify(tblClassDao.updateSelectiveByClassId(((ClassStudent) t).getTblClass()) > 0, "更新班级SQL异常");
    }

    @Override
    public <T, M> List<T> query(M query) {
        return (List<T>) tblClassDao.selectByPage((ClassQuery) query);
    }

    @Override
    public <T, M> List<T> queryByKeyword(M query) {
        return (List<T>) tblClassDao.selectByKeyword((ClassQuery) query);
    }

    /**
     * 开班申请
     *
     * @param classApplyDto
     */
    public void apply(ClassApplyDto classApplyDto){
        TblClass tblClass = tblClassDao.selectByClassId(classApplyDto.getClassId());

        // 1.申请校验
        applyVerify(tblClass);

        // 2.发起审核动作
        InitAction initAction = new InitAction(classApplyDto.getApplicant(), "", "Class_audit_template", classApplyDto.getClassId());
        initAction.execute();
    }

    /**
     * 班级结业
     *
     * @param classGraduateDto
     */
    public void graduate(ClassGraduateDto classGraduateDto){

        List<TblClassMember> tblClassMemberList = tblClassMemberDao.selectByClassId(classGraduateDto.getClassId());

        Set<String> classMemberIdSet = Sets.newHashSet();
        for (TblClassMember it: tblClassMemberList){
            if (it.getStatus() != null && it.getStatus() == 0){
                classMemberIdSet.add(it.getStudentId());
            }
        }

        for (GraduateDto it: classGraduateDto.getGraduateList()){
            if (!classMemberIdSet.contains(it.getStudentId())){
                throw new BusinessException(ResultCode.FAIL, "");
            }

            TblExamScore tblExamScore = Convert.graduateRequestDto2TblExamScore(it);

            try {
                tblExamScoreDao.insert(tblExamScore);
            }catch (DuplicateKeyException e){
                tblExamScoreDao.updateByClassIdAndStudentId(tblExamScore);
            }
        }
    }


    public List<GraduateItemDto> queryGraduate(GraduateQueryDto graduateQueryDto){
        List<TblExamScore> examScoreList = tblExamScoreDao.selectPageByClassId(graduateQueryDto.getClassId(), new RowBounds(graduateQueryDto.getOffset(), graduateQueryDto.getPageSize()));

        if (CollectionUtils.isEmpty(examScoreList)){
            return Lists.newArrayList();
        }

        List<GraduateItemDto> graduateItemDtoList = Lists.newArrayList();
        for (TblExamScore it: examScoreList){
            GraduateItemDto graduateItemDto = newGraduateItemDto(it);
            graduateItemDtoList.add(graduateItemDto);
        }

        return graduateItemDtoList;
    }

    /**
     * 开班申请校验
     *
     * @param tblClass
     */
    private void applyVerify(TblClass tblClass){
        if (tblClass == null){
            throw new BusinessException(ResultCode.FAIL, "班级不存在");
        }

        if(TblClass.Status.APPLIED == tblClass.getStatus()){
            throw new BusinessException(ResultCode.FAIL, "开班申请已提交，请勿重复申请");
        }
    }

    private GraduateItemDto newGraduateItemDto(TblExamScore tblExamScore){

        TblStudent tblStudent = tblStudentDao.selectByStudentId(tblExamScore.getStudentId());

        GraduateItemDto graduateItemDto = new GraduateItemDto();

        BeanUtils.copyProperties(tblStudent, graduateItemDto);
        graduateItemDto.setAbsentCount(10);
        graduateItemDto.setTheoryScore(tblExamScore.getTheoryScore().toString());
        graduateItemDto.setPracticeScore(tblExamScore.getPracticeScore().toString());
        graduateItemDto.setCertificate(tblExamScore.getCertificate());
        graduateItemDto.setCertificateId(tblExamScore.getCertificateId());
        return graduateItemDto;
    }
}
