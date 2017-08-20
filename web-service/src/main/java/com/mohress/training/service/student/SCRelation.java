package com.mohress.training.service.student;

import com.mohress.training.entity.TblSCRelation;
import com.mohress.training.entity.TblStudent;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 学生课程关联
 * Created by qx.wang on 2017/8/17.
 */
@Data
@AllArgsConstructor
public class SCRelation {

    private TblStudent tblStudent;

    private TblSCRelation tblSCRelation;
}
