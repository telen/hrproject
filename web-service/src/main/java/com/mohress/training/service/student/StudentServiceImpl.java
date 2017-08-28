package com.mohress.training.service.student;

import com.google.common.collect.Lists;
import com.mohress.training.dao.TblClassMemberDao;
import com.mohress.training.dao.TblStudentDao;
import com.mohress.training.entity.mclass.TblClassMember;
import com.mohress.training.entity.student.TblStudent;
import com.mohress.training.service.BaseManageService;
import com.mohress.training.util.BusiVerify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教师
 * Created by qx.wang on 2017/8/16.
 */
@Slf4j
@Service
public class StudentServiceImpl implements BaseManageService {

    private static final int DELETE_STATUS = 1;

    @Resource
    private TblStudentDao tblStudentDao;
    @Resource
    private TblClassMemberDao tblClassMemberDao;

    @Override
    @Transactional
    public <T> void newModule(T student) {
        BusiVerify.verify(tblStudentDao.insertSelective((TblStudent) student) > 0, "新增学生SQL异常");
    }

    @Override
    @Transactional
    public void delete(List<String> ids) {
        for (String id : ids) {
            BusiVerify.verify(tblStudentDao.updateStatus(id, DELETE_STATUS) > 0, "删除学生SQL异常");
        }
    }

    @Override
    public <T> void update(T student) {
        BusiVerify.verify(tblStudentDao.updateByStudentIdSelective((TblStudent) student) > 0, "更新学生SQL异常");
    }

    @Override
    public <T, M> List<T> query(M query) {
        StudentQuery q = (StudentQuery) query;
        if (q.getClassId() != null) {
            List<TblClassMember> classMembers = tblClassMemberDao.selectByClassId(q.getClassId());
            List<String> ids = Lists.newArrayList();
            for (TblClassMember member : classMembers) {
                ids.add(member.getStudentId());
            }
            if (!CollectionUtils.isEmpty(ids)) {
                q.setStudentIds(ids);
            } else {
                return null;
            }

            return (List<T>) tblStudentDao.queryStudentByStudentId(q);

        }
        return (List<T>) tblStudentDao.queryStudentList(q);
    }

    @Override
    public <T, M> List<T> queryByKeyword(M m) {
        return (List<T>) tblStudentDao.queryStudentList((StudentQuery) m);
    }

}
