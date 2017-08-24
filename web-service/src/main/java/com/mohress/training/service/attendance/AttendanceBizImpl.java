package com.mohress.training.service.attendance;

import com.google.common.base.Preconditions;
import com.mohress.training.dto.QueryDto;
import com.mohress.training.dto.attendance.AttendanceRequestDto;
import com.mohress.training.dto.attendance.AttendanceStatisticItemDto;
import com.mohress.training.entity.TblJobTime;
import com.mohress.training.entity.TblStudent;
import com.mohress.training.entity.attendance.TblAttendance;
import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.service.BaseManageService;
import com.mohress.training.service.ModuleBiz;
import com.mohress.training.service.mclass.ClassServiceImpl;
import com.mohress.training.service.student.StudentQuery;
import com.mohress.training.util.BusiVerify;
import com.mohress.training.util.Checker;
import com.mohress.training.util.Convert;
import com.mohress.training.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 考勤
 * Created by qx.wang on 2017/8/20.
 */
@Slf4j
@Service
public class AttendanceBizImpl implements ModuleBiz {

    private static final String JOB_NAME = "attendance";

    @Resource
    private BaseManageService attendanceServiceImpl;

    @Resource
    private BaseManageService studentServiceImpl;

    @Resource
    private JobService jobService;

    @Resource
    private ClassServiceImpl classServiceImpl;

    @Resource
    private AttendanceStatisticsService attendanceStatisticsService;

    @Override
    public void newModule(String o) {
        Preconditions.checkNotNull(o);
        AttendanceRequestDto attendanceRequestDto;
        try {
            attendanceRequestDto = JsonUtil.getInstance().convertToBean(AttendanceRequestDto.class, String.valueOf(o));
        } catch (Exception e) {
            throw new RuntimeException("反序列化失败");
        }

        Checker.checkNewAttendance(attendanceRequestDto);
        TblAttendance attendance = buildInsertTblAttendance(attendanceRequestDto);

        StudentQuery query = new StudentQuery();
        query.setPageIndex(0);
        query.setPageSize(10);
        query.setIdNumber(attendance.getUserId());
        List<TblStudent> students = studentServiceImpl.query(query);
        BusiVerify.verify(!CollectionUtils.isEmpty(students), "考勤学生未查询到");
        //判断该学生是否是该机构
        String agencyId = students.get(0).getAgencyId();
        //如果补打卡，判断是否为一个机构
        if (TblAttendance.Status.PATCH_CLOCK.equals(attendance.getStatus())) {

        }

        attendanceServiceImpl.newModule(attendance);
    }

    @Override
    public void delete(List<String> ids) {
        throw new RuntimeException("不支持");
    }

    @Override
    public void update(String o) {
        throw new RuntimeException("不支持");
    }

    @Override
    public Object query(QueryDto pageDto) {
        Preconditions.checkNotNull(pageDto);
        Preconditions.checkArgument(pageDto.getPage() >= 0);
        Preconditions.checkArgument(pageDto.getPageSize() > 0);
//        Preconditions.checkNotNull(pageDto.getUserId());
        //todo 增加机构参数
        List<TblAttendance> tblAgencies = attendanceServiceImpl.query(buildAttendanceQuery(pageDto));
        return Convert.convertAttendance(tblAgencies);
    }

    @Override
    public Object queryByKeyword(QueryDto queryDto) {
        Preconditions.checkNotNull(queryDto);
        Preconditions.checkArgument(queryDto.getPage() >= 0);
        Preconditions.checkArgument(queryDto.getPageSize() > 0);
        //todo 增加机构参数

        BusiVerify.verifyNotEmpty(queryDto.getKeyword(), "关键词为空");
        List<TblAttendance> tblAgencies = attendanceServiceImpl.query(buildAttendanceQuery(queryDto));
        return Convert.convertAttendance(tblAgencies);
    }

    private AttendanceQuery buildAttendanceQuery(QueryDto dto) {
        AttendanceQuery attendanceQuery = new AttendanceQuery(dto.getPage(), dto.getPageSize());
        BeanUtils.copyProperties(dto, attendanceQuery);
        return attendanceQuery;
    }

    private TblAttendance buildInsertTblAttendance(AttendanceRequestDto attendanceRequestDto) {
        TblAttendance attendance = new TblAttendance();
        BeanUtils.copyProperties(attendanceRequestDto, attendance);
        return attendance;
    }

    public List<AttendanceStatisticItemDto> queryStatistic(QueryDto pageDto) {
        //查询上次查询到本次查询时间内有无新建班级，有则统计之后录入统计表；无则查询.通过查询人找出所属人群，机构则需查本机构的

        TblJobTime tblJobTime = jobService.queryOrInsertJob(JOB_NAME);

        //todo 找到该用户agencyId
        List<TblClass> tblClasses = classServiceImpl.queryClassByRangeTime(null, tblJobTime.getTimeValue(), new Date());
        if (!CollectionUtils.isEmpty(tblClasses)) {
            //统计
            attendanceStatisticsService.buildStatistics(tblClasses, null);
        }

        return attendanceStatisticsService.query(buildAttendanceStatisticQuery(pageDto));
    }

    private AttendanceStatisticsQuery buildAttendanceStatisticQuery(QueryDto pageDto) {
        AttendanceStatisticsQuery query = new AttendanceStatisticsQuery();
        query.setClassname(pageDto.getKeyword());
        query.setAgencyName(pageDto.getKeyword());
        query.setPageIndex(pageDto.getPage());
        query.setPageSize(pageDto.getPageSize());
        //todo 找到该用户agencyId
        return query;
    }
}
