package com.mohress.training.service.attendance;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.base.Verify;
import com.mohress.training.dao.TblAttendanceDao;
import com.mohress.training.dao.TblClassDao;
import com.mohress.training.dao.TblStudentDao;
import com.mohress.training.entity.TblStudent;
import com.mohress.training.entity.attendance.TblAttendance;
import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.service.BaseManageService;
import com.mohress.training.util.BusiVerify;
import com.mohress.training.util.DateUtils;
import com.mohress.training.util.SequenceCreator;
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

    private static final Splitter SPLITTER = Splitter.on(",").trimResults().omitEmptyStrings();

    @Resource
    private TblAttendanceDao tblAttendanceDao;

    @Resource
    private TblClassDao tblClassDao;

    @Resource
    private TblStudentDao tblStudentDao;

    @Override
    public <T> void newModule(T t) {
        TblAttendance newAttendance = (TblAttendance) t;

        //判断当天是否存在，存在则添加至末尾，否则新建
        Date date = new Date();
        TblAttendance dbAttendance = tblAttendanceDao.selectByDate(newAttendance.getUserId(), DateUtils.getTodayStart(date), DateUtils.getTodayEnd(date));
        newAttendance.setStatus(getStatus(newAttendance, dbAttendance));
        if (dbAttendance == null) {
            newAttendance.setAttendanceId(SequenceCreator.getAttendanceId());
            Verify.verify(tblAttendanceDao.insertSelective(newAttendance) > 0, "新增机构SQL异常");
        } else {
            String attendanceTime = ((TblAttendance) t).getAttendanceTime();
            if (!Strings.isNullOrEmpty(attendanceTime)) {
                dbAttendance.setAttendanceTime(dbAttendance.getAttendanceTime() + "," + attendanceTime);
            }
            BusiVerify.verify(tblAttendanceDao.updateByPrimaryKeySelective(dbAttendance) > 0, "更新考勤记录SQL异常");
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

    /**
     * 获取时间
     */
    private Integer getStatus(TblAttendance newAttendance, TblAttendance dbAttendance) {
        String attendanceTime = newAttendance.getAttendanceTime();
        if (TblAttendance.Status.PATCH_CLOCK.equals(newAttendance.getStatus()) ||
                TblAttendance.Status.LEAVE.equals(newAttendance.getStatus())) {
            return newAttendance.getStatus();
        }

        if (dbAttendance == null) {
            return TblAttendance.Status.EXCEPTION;
        }

        TblClass tblClass = tblClassDao.selectByClassId(dbAttendance.getClassId());
        BusiVerify.verifyNotNull(tblClass, "查询班级为空");
        Date currentTime = new Date(Long.parseLong(attendanceTime));
        String dbTime = dbAttendance.getAttendanceTime();
        List<String> timeList = SPLITTER.splitToList(dbTime);
        Date firstTime = new Date(Long.valueOf(timeList.get(0)));
        //根据课程ID查询上下课时间

        if (firstTime.after(tblClass.getOnClassTime())) {
            return TblAttendance.Status.BE_LATE;
        } else if (currentTime.before(tblClass.getOffClassTime())) {
            return TblAttendance.Status.LEAVE_EARLY;
        }
        return TblAttendance.Status.NORMAL;
    }
}
