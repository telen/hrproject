package com.mohress.training.service.teacher;

import com.google.common.base.Verify;
import com.mohress.training.dao.TblTeacherDao;
import com.mohress.training.entity.TblTeacher;
import com.mohress.training.service.BaseManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教师
 * Created by qx.wang on 2017/8/16.
 */
@Slf4j
@Service
public class TeacherServiceImpl implements BaseManageService {

    private static final int DELETE_STATUS = 1;

    @Resource
    private TblTeacherDao tblTeacherDao;

    @Override
    public <T> void newModule(T teacher) {
        Verify.verify(tblTeacherDao.insertSelective((TblTeacher)teacher) > 0, "新增教师SQL异常");

    }

    @Override
    @Transactional
    public void delete(List<String> ids) {
        for (String id : ids) {
            Verify.verify(tblTeacherDao.updateStatus(id, DELETE_STATUS) > 0, "删除教师SQL异常");
        }
    }

    @Override
    public <T> void update(T teacher) {
        Verify.verify(tblTeacherDao.updateByTeacherIdSelective((TblTeacher)teacher) > 0, "更新教师SQL异常");
    }

    @Override
    public <T, M> List<T> query(M m) {
        return (List<T>)tblTeacherDao.queryTeacherList((TeacherQuery)m);
    }

    @Override
    public <T, M> List<T> queryByKeyword(M m) {
        return (List<T>)tblTeacherDao.queryTeacherByKeyword((TeacherQuery)m);
    }

}
