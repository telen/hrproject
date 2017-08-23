package com.mohress.training.controller;

import com.mohress.training.dto.Response;
import com.mohress.training.dto.mclass.ClassApplyDto;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.service.mclass.ClassServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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

        classApplyDto.setApplicant(userId);

        classService.apply(classApplyDto);

        return new Response(ResultCode.SUCCESS.getCode(), "开班审核已提交，等待审核。");
    }
}
