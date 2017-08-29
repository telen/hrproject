package com.mohress.training.controller;

import com.mohress.training.dto.Response;
import com.mohress.training.dto.security.AccountAssignRequestDto;
import com.mohress.training.dto.security.AccountDetailDto;
import com.mohress.training.dto.security.AuthorityDto;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.service.security.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
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

    @ResponseBody
    @RequestMapping("accounts")
    public Response<List<AccountDetailDto>> accountList(@CookieValue("token") String userId){

        List<AccountDetailDto> accountDetailDtoList = securityService.queryAccountList(userId);

        return new Response<>(ResultCode.SUCCESS.getCode(), "账号列表查询成功", accountDetailDtoList);
    }

    @ResponseBody
    @RequestMapping("assign")
    public Response assignAccount(@CookieValue("token") String userId, @RequestBody AccountAssignRequestDto accountAssignRequestDto){

        securityService.assignAccount(userId, accountAssignRequestDto);

        return new Response(ResultCode.SUCCESS.getCode(), "账号分配成功");
    }
}
