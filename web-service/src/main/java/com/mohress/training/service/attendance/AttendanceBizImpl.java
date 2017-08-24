package com.mohress.training.service.attendance;

import com.google.common.base.Preconditions;
import com.mohress.training.dto.QueryDto;
import com.mohress.training.dto.attendance.AttendanceRequestDto;
import com.mohress.training.entity.student.TblStudent;
import com.mohress.training.entity.attendance.TblAttendance;
import com.mohress.training.service.BaseManageService;
import com.mohress.training.service.ModuleBiz;
import com.mohress.training.service.student.StudentQuery;
import com.mohress.training.util.*;
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
    private BaseManageService attendanceServiceImpl;

    @Resource
    private BaseManageService studentServiceImpl;

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
        if(TblAttendance.Status.PATCH_CLOCK.equals(attendance.getStatus())){

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

}
