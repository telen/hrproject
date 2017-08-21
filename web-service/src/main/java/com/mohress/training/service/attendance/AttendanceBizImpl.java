package com.mohress.training.service.attendance;

import com.google.common.base.Preconditions;
import com.mohress.training.dto.QueryDto;
import com.mohress.training.dto.attendance.AttendanceItemDto;
import com.mohress.training.dto.attendance.AttendanceRequestDto;
import com.mohress.training.entity.attendance.TblAttendance;
import com.mohress.training.service.BaseManageService;
import com.mohress.training.service.ModuleBiz;
import com.mohress.training.util.BusiVerify;
import com.mohress.training.util.Convert;
import com.mohress.training.util.JsonUtil;
import com.mohress.training.util.SequenceCreator;
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

    @Override
    public void newModule(String o) {
        Preconditions.checkNotNull(o);
        AttendanceRequestDto attendanceRequestDto = null;
        try {
            attendanceRequestDto = JsonUtil.getInstance().convertToBean(AttendanceRequestDto.class, String.valueOf(o));
        } catch (Exception e) {
            throw new RuntimeException("反序列化失败");
        }
        //todo 设置打卡时间
        attendanceServiceImpl.newModule(buildInsertTblAttendance(attendanceRequestDto));
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
        List<AttendanceItemDto> attendanceItemDtos = Convert.convertAttendance(tblAgencies);
        //todo 填充首尾打卡时间

        return attendanceItemDtos;
    }

    @Override
    public Object queryByKeyword(QueryDto queryDto) {
        Preconditions.checkNotNull(queryDto);
        Preconditions.checkArgument(queryDto.getPage() >= 0);
        Preconditions.checkArgument(queryDto.getPageSize() > 0);
        BusiVerify.verifyNotEmpty(queryDto.getKeyword(), "关键词为空");
        List<TblAttendance> tblAgencies = attendanceServiceImpl.query(buildAttendanceQuery(queryDto));
        List<AttendanceItemDto> attendanceItemDtos = Convert.convertAttendance(tblAgencies);
        //todo 填充首尾打卡时间

        return attendanceItemDtos;
    }

    private AttendanceQuery buildAttendanceQuery(QueryDto dto) {
        return new AttendanceQuery(dto.getPageSize(), dto.getPage());
    }

    private TblAttendance buildInsertTblAttendance(AttendanceRequestDto attendanceRequestDto) {
        TblAttendance attendance = new TblAttendance();
        BeanUtils.copyProperties(attendanceRequestDto, attendance);
        attendance.setAttendanceId(SequenceCreator.getAttendanceId());
        return attendance;
    }

}
