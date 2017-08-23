package com.mohress.training.service.mclass;

import com.mohress.training.dao.TblClassDao;
import com.mohress.training.dao.TblClassMemberDao;
import com.mohress.training.entity.mclass.TblClassMember;
import com.google.common.base.Verify;
import com.mohress.training.dao.*;
import com.mohress.training.dto.mclass.ClassApplyDto;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.entity.audit.TblAuditTemplate;
import com.mohress.training.entity.audit.TblClassAuditRecord;
import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.enums.AuditStatus;
import com.mohress.training.service.BaseManageService;
import com.mohress.training.util.BusiVerify;
import com.mohress.training.service.audit.action.InitAction;
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
    private static final int DELETE_STATUS = 1;

    @Resource
    private TblClassDao tblClassDao;

    @Resource
    private TblClassMemberDao tblClassMemberDao;

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

    public void alterStatus(String classId, int beforeStatus, int toStatus) {
        Verify.verify(tblClassDao.updateByClassId(classId, beforeStatus, toStatus) > 0);
    }

    public void apply(ClassApplyDto classApplyDto){
        InitAction initAction = new InitAction(classApplyDto.getApplicant(), "", "", classApplyDto.getClassId());
        initAction.execute();
    }
}
