package com.mohress.training.service.course;

import com.google.common.base.Verify;
import com.mohress.training.dao.TblCourseDao;
import com.mohress.training.entity.TblCourse;
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
public class CourseServiceImpl implements BaseManageService {

    private static final int DELETE_STATUS = 1;

    @Resource
    private TblCourseDao tblCourseDao;

    @Override
    @Transactional
    public <T> void newModule(T course) {
        Verify.verify(tblCourseDao.insertSelective((TblCourse) course) > 0, "新增学生SQL异常");
    }

    @Override
    @Transactional
    public void delete(List<String> ids) {
        for (String id : ids) {
            Verify.verify(tblCourseDao.updateStatus(id, DELETE_STATUS) > 0, "删除学生SQL异常");
        }
    }

    @Override
    public <T> void update(T course) {
        Verify.verify(tblCourseDao.updateByCourseIdSelective((TblCourse) course) > 0, "更新学生SQL异常");
    }

    @Override
    public <T, M> List<T> query(M m) {
        return (List<T>) tblCourseDao.queryCourseList((CourseQuery) m);
    }

    @Override
    public <T, M> List<T> queryByKeyword(M m) {
        return (List<T>) tblCourseDao.queryCourseByKeyword((CourseQuery) m);
    }

}
