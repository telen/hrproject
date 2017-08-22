package com.mohress.training.dao;

import com.mohress.training.entity.mclass.TblClassMember;

import java.util.List;

/**
 * 班级学员
 * Created by qx.wang on 2017/8/17.
 */
public interface TblClassMemberDao {

    int insertBatchSelective(List<TblClassMember> members);

    int deleteByClassId(String classId);
}
