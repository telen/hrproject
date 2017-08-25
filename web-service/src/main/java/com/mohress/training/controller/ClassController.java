package com.mohress.training.controller;

import com.mohress.training.dto.Response;
import com.mohress.training.dto.mclass.ClassApplyDto;
import com.mohress.training.dto.mclass.ClassGraduateDto;
import com.mohress.training.dto.student.GraduateItemDto;
import com.mohress.training.dto.student.GraduateQueryDto;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.service.mclass.ClassServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 培训班级
 * <p>
 * Created by youtao.wan on 2017/8/9.
 */
@Slf4j
@Controller
@RequestMapping("api/class/")
public class ClassController {

    @Resource
    private ClassServiceImpl classService;

    /**
     * 开班申请
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("apply")
    public Response apply(@CookieValue("token") String userId, @RequestBody ClassApplyDto classApplyDto){

        classApplyDto.setApplicant("17081815504021040603");

        classService.apply(classApplyDto);

        return new Response(ResultCode.SUCCESS.getCode(), "开班审核已提交，等待审核。");
    }

    /**
     * 班级结业
     *
     * @param classGraduateDto
     * @return
     */
    @ResponseBody
    @RequestMapping("graduate")
    public Response graduate(@RequestBody ClassGraduateDto classGraduateDto){

        classService.graduate(classGraduateDto);

        return new Response(ResultCode.SUCCESS.getCode(), "结业信息已保存");
    }

    /**
     * 班级学员毕业信息查询
     *
     * @param classId
     * @param pageSize
     * @param pageIndex
     * @return
     */
    @ResponseBody
    @RequestMapping("queryGraduate")
    public Response<List<GraduateItemDto>> queryGraduate(String classId, Integer pageSize, Integer pageIndex){

        GraduateQueryDto graduateQueryDto = new GraduateQueryDto();
        graduateQueryDto.setClassId(classId);
        graduateQueryDto.setPageSize(pageSize);
        graduateQueryDto.setPageIndex(pageIndex);

        List<GraduateItemDto> list = classService.queryGraduate(graduateQueryDto);

        return new Response<>(ResultCode.SUCCESS.getCode(), "", list);
    }
}
