package com.mohress.training.controller;

import com.google.common.base.Preconditions;
import com.mohress.training.dto.Response;
import com.mohress.training.dto.Responses;
import com.mohress.training.dto.mclass.ClassApplyDto;
import com.mohress.training.dto.mclass.ClassRequestDto;
import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.service.mclass.ClassServiceImpl;
import com.mohress.training.service.mclass.ClassStudent;
import com.mohress.training.util.JsonUtil;
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
    public Response apply(@CookieValue("token") String userId, @RequestBody ClassApplyDto classApplyDto) {

        classApplyDto.setApplicant("17081815504021040603");

        classService.apply(classApplyDto);

        return new Response(ResultCode.SUCCESS.getCode(), "开班审核已提交，等待审核。");
    }

    @ResponseBody
    @RequestMapping("inspection")
    public Response<Boolean> apply(@CookieValue("token") String userId, @RequestBody ClassRequestDto dto) {
        Preconditions.checkNotNull(dto);
        Preconditions.checkArgument(dto.getInspectionStatus() != null, "抽查状态为空");
        ClassRequestDto classRequestDto = null;
        try {
            classRequestDto = JsonUtil.getInstance().convertToBean(ClassRequestDto.class, String.valueOf(dto));
        } catch (Exception e) {
            log.error("新建机构反序列化失败 {}", dto, e);
        }
        classService.update(buildUpdateClass(classRequestDto));
        return Responses.succ(Boolean.TRUE);
    }

    private ClassStudent buildUpdateClass(ClassRequestDto classRequestDto) {
        TblClass tblClass = new TblClass();
        tblClass.setClassId(classRequestDto.getClassId());
        tblClass.setInspectionStatus(classRequestDto.getInspectionStatus());
        return new ClassStudent(tblClass, null);
    }
}
