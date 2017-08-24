package com.mohress.training.service.attendance;

import com.mohress.training.dao.TblJobTimeDao;
import com.mohress.training.entity.TblJobTime;
import com.mohress.training.util.BusiVerify;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * job
 * Created by qx.wang on 2017/8/24.
 */
@Service
public class JobService {

    @Resource
    private TblJobTimeDao tblJobTimeDao;

    public TblJobTime queryOrInsertJob(String jobName) {
        TblJobTime tblJobTime = tblJobTimeDao.selectByJobName(jobName);
        if (tblJobTime == null) {
            tblJobTime = new TblJobTime();
            tblJobTime.setTimeValue(new Date());
            BusiVerify.verify(tblJobTimeDao.insertSelective(tblJobTime) > 0, "新增 jobTime SQL失败");
            tblJobTime.setTimeValue(new Date(0));
        }

        return tblJobTime;
    }
}
