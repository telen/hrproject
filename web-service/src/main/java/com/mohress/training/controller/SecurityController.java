package com.mohress.training.controller;

import com.mohress.training.dto.Response;
import com.mohress.training.dto.SecurityDto;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;
import com.mohress.training.service.security.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限接口
 *
 */
@Slf4j
@Controller
@RequestMapping("api/security/")
public class SecurityController {

    @Resource
    private SecurityService securityService;


    @ResponseBody
    @RequestMapping("menus")
    public Response<List<SecurityDto>> menuList(){
        List<SecurityDto> securityDtoList = securityService.queryAuthorityList();
        return new Response<>(ResultCode.SUCCESS.getCode(), "权限查询成功", securityDtoList);
    }
}
