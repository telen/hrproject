package com.mohress.training.service.attendance;

import com.google.common.base.Preconditions;
import com.mohress.training.dto.QueryDto;
import com.mohress.training.dto.attendance.AttendanceRequestDto;
import com.mohress.training.dto.attendance.AttendanceStatisticItemDto;
import com.mohress.training.entity.attendance.TblAttendance;
import com.mohress.training.entity.attendance.TblAttendanceStatistics;
import com.mohress.training.entity.student.TblStudent;
import com.mohress.training.service.BaseManageService;
import com.mohress.training.service.ModuleBiz;
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
import java.util.List;

/**
 * 考勤
 * Created by qx.wang on 2017/8/20.
 */
@Slf4j
@Service
public class AttendanceBizImpl implements ModuleBiz {


    @Resource
    private BaseManageService studentServiceImpl;

    @Resource
    private BaseManageService attendanceServiceImpl;

    @Resource
    private AttendanceStatisticsService attendanceStatisticsService;

    @Override
    public void newModule(String o, String agencyId) {
        Preconditions.checkNotNull(o);
        AttendanceRequestDto attendanceRequestDto;
        try {
            attendanceRequestDto = JsonUtil.getInstance().convertToBean(AttendanceRequestDto.class, String.valueOf(o));
        } catch (Exception e) {
            throw new RuntimeException("反序列化失败");
        }

        Checker.checkNewAttendance(attendanceRequestDto);
        TblAttendance attendance = buildInsertTblAttendance(attendanceRequestDto, agencyId);

        StudentQuery query = new StudentQuery();
        query.setIdNumber(attendance.getUserId());
        List<TblStudent> students = studentServiceImpl.query(query);
        BusiVerify.verify(!CollectionUtils.isEmpty(students), "考勤学生未查询到");
        //如果补打卡，判断是否为一个机构
        if (TblAttendance.Status.PATCH_CLOCK.equals(attendance.getStatus())) {
            BusiVerify.verify(students.get(0).getAgencyId().equals(agencyId), "学生不是该机构，不可进行此操作" + o + agencyId);
        }
        attendance.setUserId(students.get(0).getStudentId());

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
        List<TblAttendance> tblAgencies = attendanceServiceImpl.query(buildAttendanceQuery(pageDto));
        return Convert.convertAttendance(tblAgencies);
    }

    @Override
    public void checkDelete(String agencyId, List<String> ids) {
        return;
    }

    public List<AttendanceStatisticItemDto> queryStatistic(QueryDto pageDto, String agencyId) {

        List<TblAttendanceStatistics> statisticItems = attendanceStatisticsService.query(buildAttendanceStatisticQuery(pageDto, agencyId));

        return Convert.convertStatistic(statisticItems);
    }

    private AttendanceQuery buildAttendanceQuery(QueryDto dto) {
        AttendanceQuery attendanceQuery = new AttendanceQuery(dto.getPage(), dto.getPageSize());
        BeanUtils.copyProperties(dto, attendanceQuery);
        return attendanceQuery;
    }

    private TblAttendance buildInsertTblAttendance(AttendanceRequestDto attendanceRequestDto, String agencyId) {
        TblAttendance attendance = new TblAttendance();
        attendance.setAgencyId(agencyId);
        BeanUtils.copyProperties(attendanceRequestDto, attendance);
        return attendance;
    }

    private AttendanceStatisticsQuery buildAttendanceStatisticQuery(QueryDto pageDto, String agencyId) {
        AttendanceStatisticsQuery query = new AttendanceStatisticsQuery();
        query.setAgencyId(agencyId);
        query.setClassname(pageDto.getKeyword());
        query.setAgencyName(pageDto.getKeyword());
        query.setPageIndex(pageDto.getPage());
        query.setPageSize(pageDto.getPageSize());
        return query;
    }
}
