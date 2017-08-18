package com.mohress.training.controller;

import com.mohress.training.cache.AccountAuthorityCache;
import com.mohress.training.dto.Response;
import com.mohress.training.dto.UserDto;
import com.mohress.training.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 账号接口
 *
 */
@Slf4j
@Controller
@RequestMapping("api/user/")
public class AccountController {

    @Resource
    private AccountAuthorityCache cache;

    @ResponseBody
    @RequestMapping
    public Response<UserDto> user(@CookieValue("token") String userId){


        return new Response<>(ResultCode.SUCCESS.getCode(), "成功", null);
    }

    @ResponseBody
    @RequestMapping(value = "reset")
    public Response resetPassword(){
        return new Response(ResultCode.SUCCESS.getCode(), "成功");
    }

}
