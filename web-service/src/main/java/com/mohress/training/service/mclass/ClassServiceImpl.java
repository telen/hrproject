package com.mohress.training.service.mclass;

import com.mohress.training.dao.TblClassDao;
import com.mohress.training.dao.TblClassMemberDao;
import com.mohress.training.dto.mclass.ClassApplyDto;
import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.entity.mclass.TblClassMember;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;
import com.mohress.training.service.BaseManageService;
import com.mohress.training.service.audit.action.InitAction;
import com.mohress.training.util.BusiVerify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 班级管理
 * Created by qx.wang on 2017/8/17.
 */
@Slf4j
@Service
public class ClassServiceImpl implements BaseManageService {

    @Resource
    private TblClassDao tblClassDao;

    @Resource
    private TblClassMemberDao tblClassMemberDao;

    @Override
    @Transactional
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
            BusiVerify.verify(tblClassDao.updateStatus(id, TblClass.Status.DELETE) > 0, "更新班级删除状态SQL失败");
        }
    }

    @Override
    @Transactional
    public <T> void update(T t) {
        TblClass tblClass = ((ClassStudent) t).getTblClass();
        BusiVerify.verify(tblClassDao.updateSelectiveByClassId(tblClass) > 0, "更新班级SQL异常");
        BusiVerify.verify(tblClassMemberDao.deleteByClassId(tblClass.getClassId()) > 0, "删除班级关联学生SQL失败");
        List<TblClassMember> tblClassMembers = ((ClassStudent) t).getTblClassMembers();
        if (!CollectionUtils.isEmpty(tblClassMembers)) {
            BusiVerify.verify(tblClassMemberDao.insertBatchSelective(tblClassMembers) > 0, "新增班级SQL异常");
        }
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
    public void apply(ClassApplyDto classApplyDto) {
        TblClass tblClass = tblClassDao.selectByClassId(classApplyDto.getClassId());

        // 1.申请校验
        applyVerify(tblClass);

        // 2.发起审核动作
        InitAction initAction = new InitAction(classApplyDto.getApplicant(), "", "Class_audit_template", classApplyDto.getClassId());
        initAction.execute();
    }

    /**
     * 开班申请校验
     *
     * @param tblClass
     */
    private void applyVerify(TblClass tblClass) {
        if (tblClass == null) {
            throw new BusinessException(ResultCode.FAIL, "班级不存在");
        }

        if (TblClass.Status.APPLIED == tblClass.getStatus()) {
            throw new BusinessException(ResultCode.FAIL, "开班申请已提交，请勿重复申请");
        }
    }

    public List<TblClass> queryClassByRangeTime(String agencyId, Date startTime, Date endTime) {
        return tblClassDao.selectByRangeTime(agencyId, startTime, endTime);
    }

    public void updateStatus(TblClass tblClass) {
        BusiVerify.verify(tblClassDao.updateStatusByClassId(tblClass) > 0, "更新检查状态SQL失败");
    }
}
