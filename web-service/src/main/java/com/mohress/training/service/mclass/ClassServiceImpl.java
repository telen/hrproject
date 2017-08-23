package com.mohress.training.service.mclass;

import com.google.common.base.Verify;
import com.mohress.training.dao.*;
import com.mohress.training.dto.mclass.ClassApplyDto;
import com.mohress.training.entity.audit.TblAuditFlow;
import com.mohress.training.entity.audit.TblAuditTemplate;
import com.mohress.training.entity.audit.TblClassAuditRecord;
import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.enums.AuditStatus;
import com.mohress.training.service.BaseManageService;
import com.mohress.training.service.audit.action.InitAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Verify.verify(tblClassDao.insertSelective(((ClassStudent) t).getTblClass()) > 0);
    }

    @Override
    @Transactional
    public void delete(List<String> ids) {
        for (String id : ids) {
            Verify.verify(tblClassDao.updateStatus(id, DELETE_STATUS) > 0);
            Verify.verify(tblClassMemberDao.updateBatchStatusByClassId(id) > 0);
        }

    }

    @Override
    public <T> void update(T t) {
        Verify.verify(tblClassDao.updateSelectiveByClassId(((ClassStudent) t).getTblClass()) > 0);
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
