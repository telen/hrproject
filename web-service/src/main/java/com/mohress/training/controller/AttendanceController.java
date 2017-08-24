package com.mohress.training.controller;

import com.mohress.training.dto.QueryDto;
import com.mohress.training.dto.Response;
import com.mohress.training.dto.Responses;
import com.mohress.training.dto.attendance.AttendanceStatisticItemDto;
import com.mohress.training.service.attendance.AttendanceBizImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 学员考勤
 * <p>
 * Created by youtao.wan on 2017/8/9.
 */
@Slf4j
@Controller
@RequestMapping("api/attendance")
public class AttendanceController {

    @Resource
    private AttendanceBizImpl attendanceBizImpl;

    @RequestMapping(name = "statistic")
    @ResponseBody
    public Response<List<AttendanceStatisticItemDto>> statistic(@RequestBody QueryDto pageDto) {
        if (pageDto == null) {
            pageDto = new QueryDto();
        }
        if (pageDto.getPage() == null || pageDto.getPage() < 0) {
            pageDto.setPage(0);
            pageDto.setPageSize(10);
        } else {
            pageDto.setPage(pageDto.getPage() - 1);
        }
//        String userId = CipherUtil.decryptName(encryptedName);
        String userId = null;
        pageDto.setUserId(userId);
        log.info("userId-{}, 考勤统计查询,查询条件 {}", userId, pageDto);

        List<AttendanceStatisticItemDto> dto = attendanceBizImpl.queryStatistic(pageDto);
        log.info("userId-{} 考勤统计查询 {}，返回 {}", userId, pageDto, dto);
        return Responses.succ(dto);
    }
}
