package com.mohress.training.service.mclass;

import com.google.common.base.Verify;
import com.mohress.training.dao.TblClassDao;
import com.mohress.training.dao.TblClassMemberDao;
import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.service.BaseManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
        Verify.verify(tblClassDao.insertSelective((TblClass) t) > 0);
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
        Verify.verify(tblClassDao.updateSelectiveByClassId((TblClass) t) > 0);
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
}
