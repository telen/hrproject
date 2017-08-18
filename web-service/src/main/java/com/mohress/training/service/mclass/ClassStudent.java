package com.mohress.training.service.mclass;

import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.entity.mclass.TblClassMember;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 *
 * Created by qx.wang on 2017/8/17.
 */
@Data
@AllArgsConstructor
public class ClassStudent {

    private TblClass tblClass;

    private List<TblClassMember> tblClassMembers;

}
