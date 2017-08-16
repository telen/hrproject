package com.mohress.training.controller;

import com.mohress.training.dto.PageDto;
import com.mohress.training.dto.Response;
import com.mohress.training.dto.Responses;
import com.mohress.training.dto.teacher.TeacherListResponseDto;
import com.mohress.training.entity.TblTeacher;
import com.mohress.training.service.BaseQuery;
import com.mohress.training.service.teacher.TeacherQuery;
import com.mohress.training.service.teacher.TeacherService;
import com.mohress.training.util.CipherUtil;
import com.mohress.training.util.Convert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 培训老师
 * <p>
 * Created by youtao.wan on 2017/8/9.
 */
@Slf4j
@Controller
@RequestMapping("api/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherServiceImpl;


    @ResponseBody
    @RequestMapping(value = "query/{teacherId}")
    public Response<TeacherListResponseDto> query(@CookieValue(name = "token") String encryptedName, @PathVariable String teacherId, @RequestBody PageDto dto) {
        String userId = CipherUtil.decryptName(encryptedName);
        log.info("userId-{}, 查询机构 {} ,查询条件 {}", userId, teacherId, dto);

        List<TblTeacher> tblAgencies = teacherServiceImpl.query(buildTeacherQuery(dto));
        TeacherListResponseDto responseDto = Convert.convertTeacher(tblAgencies);
        log.info("userId-{}, 查询机构 {}, 返回 {}", userId, teacherId, responseDto);
        return Responses.succ(responseDto);
    }

    private TeacherQuery buildTeacherQuery(PageDto dto) {
        TeacherQuery query = new TeacherQuery();
        query.setPageIndex(dto.getPage());
        query.setPageSize(dto.getPageSize());
        return query;
    }

}
