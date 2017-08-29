package com.mohress.training.service.attendance;

import com.mohress.training.dao.TblJobTimeDao;
import com.mohress.training.entity.TblJobTime;
import com.mohress.training.util.BusiVerify;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * job
 * Created by qx.wang on 2017/8/24.
 */
@Service
public class JobService {
    private static final String JOB_NAME = "attendance";

    @Resource
    private TblJobTimeDao tblJobTimeDao;

    @Resource
    private AttendanceStatisticsService attendanceStatisticsService;

    public TblJobTime queryJob(String jobName) {
        return tblJobTimeDao.selectByJobName(jobName);
    }

    public void newJob(TblJobTime tblJobTime) {
        BusiVerify.verify(tblJobTimeDao.insertSelective(tblJobTime) > 0, "新增 jobTime SQL失败");
    }

    public void updateJob(TblJobTime tblJobTime) {
        BusiVerify.verify(tblJobTimeDao.updateByPrimaryKeySelective(tblJobTime) > 0, "更新 jobTime SQL失败");
    }

    @PostConstruct
    public void init() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                TblJobTime tblJobTime = queryJob(JOB_NAME);


                Date timeValue;
                if (tblJobTime == null) {
                    timeValue = new Date(0);
                } else {
                    timeValue = tblJobTime.getTimeValue();
                }

                Date endTime = new Date();

                attendanceStatisticsService.buildStatistics(timeValue);


                if (timeValue.getTime() == 0) {
                    newJob(newTimeJob(JOB_NAME));
                } else {
                    tblJobTime.setTimeValue(endTime);
                    updateJob(tblJobTime);
                }
            }

            private TblJobTime newTimeJob(String jobName) {
                TblJobTime jobTime = new TblJobTime();
                jobTime.setTimeValue(new Date());
                jobTime.setJobName(jobName);
                return jobTime;
            }
        }, 10, 12, TimeUnit.SECONDS);
    }
}
