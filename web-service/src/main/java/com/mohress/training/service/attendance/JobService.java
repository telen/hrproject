package com.mohress.training.service.attendance;

import com.mohress.training.dao.TblJobTimeDao;
import com.mohress.training.entity.TblJobTime;
import com.mohress.training.util.BusiVerify;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * job
 * Created by qx.wang on 2017/8/24.
 */
@Service
public class JobService {

    @Resource
    private TblJobTimeDao tblJobTimeDao;

    public TblJobTime queryJob(String jobName) {
        return tblJobTimeDao.selectByJobName(jobName);
    }

    public void newJob(TblJobTime tblJobTime) {
        BusiVerify.verify(tblJobTimeDao.insertSelective(tblJobTime) > 0, "新增 jobTime SQL失败");
    }

    public void updateJob(TblJobTime tblJobTime) {
        BusiVerify.verify(tblJobTimeDao.updateByPrimaryKeySelective(tblJobTime) > 0, "更新 jobTime SQL失败");
    }
}
