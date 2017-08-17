package com.mohress.training.service.teacher;

import com.mohress.training.entity.TblTeacher;
import com.mohress.training.service.BaseQuery;

import java.util.List;

/**
 * 教师服务
 * Created by qx.wang on 2017/8/16.
 */
public interface TeacherService {
    /**
     * 新增
     */
    void newModule(TblTeacher teacher);

    /**
     * 批量删除
     */
    void delete(List<String> ids);

    /**
     * 更新
     */
    void update(TblTeacher teacher);

    /**
     * 查询
     */
    List<TblTeacher> query(TeacherQuery query);

    List<TblTeacher> queryByKeyword(TeacherQuery teacherQuery);
}
