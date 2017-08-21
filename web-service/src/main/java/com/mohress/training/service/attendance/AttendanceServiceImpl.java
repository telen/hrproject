package com.mohress.training.service.attendance;

import com.google.common.base.Verify;
import com.mohress.training.dao.TblAttendanceDao;
import com.mohress.training.entity.attendance.TblAttendance;
import com.mohress.training.service.BaseManageService;
import com.mohress.training.util.BusiVerify;
import com.mohress.training.util.DateUtils;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 考勤
 * Created by qx.wang on 2017/8/20.
 */
@Data
@Service
public class AttendanceServiceImpl implements BaseManageService {
    private static final int DELETE_STATUS = 1;

    @Resource
    private TblAttendanceDao tblAttendanceDao;

    @Override
    public <T> void newModule(T t) {
        //判断当天是否存在，存在则添加至末尾，否则新建
        Date date = new Date();
        TblAttendance attendance = tblAttendanceDao.selectByDate(DateUtils.getTodayStart(date), DateUtils.getTodayEnd(date));
        if (attendance == null) {
            Verify.verify(tblAttendanceDao.insertSelective((TblAttendance) t) > 0, "新增机构SQL异常");
        } else {
            attendance.setAttendanceTime(attendance.getAttendanceTime() + "," + ((TblAttendance) t).getAttendanceTime());
            BusiVerify.verify(tblAttendanceDao.updateByPrimaryIdSelective(attendance) > 0, "更新考勤记录SQL异常");
        }
    }

    @Override
    public <T> void update(T t) {
        throw new RuntimeException("暂不支持");
    }

    @Override
    public <T, M> List<T> query(M query) {
        return (List<T>) tblAttendanceDao.selectByKeyword((AttendanceQuery) query);
    }

    @Override
    public <T, M> List<T> queryByKeyword(M m) {
        throw new RuntimeException("暂不支持");
    }

    @Override
    public void delete(List<String> attendanceIds) {
        throw new RuntimeException("暂不支持");
    }
}
