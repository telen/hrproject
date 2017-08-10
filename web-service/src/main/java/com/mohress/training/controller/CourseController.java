package com.mohress.training.controller;

import com.mohress.training.dto.CourseDto;
import com.mohress.training.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 培训课程
 *
 * Created by youtao.wan on 2017/8/9.
 */
@Slf4j
@Controller
@RequestMapping("api/course/")
public class CourseController {

    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Response addCourse(){
        return null;
    }


    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Response updateCourse(){
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public Response deleteCourse(){
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "query")
    public Response<CourseDto> queryCourse(){
        return null;
    }
}
