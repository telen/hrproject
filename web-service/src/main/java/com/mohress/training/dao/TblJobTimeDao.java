package com.mohress.training.dao;

import com.mohress.training.entity.TblJobTime;

/**
 * 任务时间
 * Created by qx.wang on 2017/8/24.
 */
public interface TblJobTimeDao {

    int insertSelective(TblJobTime tblJobTime);

    TblJobTime selectByJobName(String jobName);

    int updateByPrimaryKeySelective(TblJobTime tblJobTime);

}
