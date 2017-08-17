package com.mohress.training.service.teacher;

import com.google.common.base.Verify;
import com.mohress.training.dao.TblTeacherDao;
import com.mohress.training.entity.TblTeacher;
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
public class TeacherServiceImpl implements TeacherService {

    private static final int DELETE_STATUS = 1;
    @Resource
    private TblTeacherDao tblTeacherDao;

    @Override
    public void newModule(TblTeacher teacher) {
        Verify.verify(tblTeacherDao.insertSelective(teacher) > 0, "新增机构SQL异常");

    }

    @Override
    @Transactional
    public void delete(List<String> ids) {
        for (String id : ids) {
            Verify.verify(tblTeacherDao.updateStatus(id, DELETE_STATUS) > 0, "删除机构SQL异常");
        }
    }

    @Override
    public void update(TblTeacher teacher) {
        Verify.verify(tblTeacherDao.updateByPrimaryKeySelective(teacher) > 0, "更新机构SQL异常");
    }

    @Override
    public List<TblTeacher> query(TeacherQuery query) {
        return tblTeacherDao.queryTeacherList(query);
    }

    @Override
    public List<TblTeacher> queryByKeyword(TeacherQuery teacherQuery) {
        return tblTeacherDao.queryTeacherByKeyword(teacherQuery);
    }
}
