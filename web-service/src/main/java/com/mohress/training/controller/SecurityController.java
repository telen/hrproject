package com.mohress.training.controller;

import com.mohress.training.dto.security.AuthorityManagerDto;
import com.mohress.training.dto.Response;
import com.mohress.training.dto.security.AuthorityDto;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.service.security.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
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

    /**
     * 查询所有权限列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("menus")
    public Response<List<AuthorityDto>> menuList(){
        List<AuthorityDto> authorityDtoList = securityService.queryAuthorityList();
        return new Response<>(ResultCode.SUCCESS.getCode(), "权限查询成功", authorityDtoList);
    }

    /**
     * 给账号分配权限
     *
     * @param userId
     * @param authorityManagerDto
     * @return
     */
    @ResponseBody
    @RequestMapping("assign")
    public Response assignAuthority(@CookieValue("token") String userId, AuthorityManagerDto authorityManagerDto){
        return null;
    }

    /**
     * 给账号解除权限
     *
     * @param userId
     * @param authorityManagerDto
     * @return
     */
    @ResponseBody
    @RequestMapping("retrieve")
    public Response retrieveAuthority(@CookieValue("token") String userId, AuthorityManagerDto authorityManagerDto){
        return null;
    }
}
