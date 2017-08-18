package com.mohress.training.controller;

import com.mohress.training.dto.Response;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 返回所有的菜单编码
 *
 */
@Slf4j
@Controller
@RequestMapping("api/menu/")
public class MenuController {


    @ResponseBody
    @RequestMapping("list")
    public Response menuList(){
        throw new BusinessException(ResultCode.AUDIT_FAIL, "异常测试");
    }
}
