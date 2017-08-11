package com.mohress.training.controller;

import com.mohress.training.dto.Response;
import com.mohress.training.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 账号接口
 *
 */
@Slf4j
@Controller
@RequestMapping("api/user/")
public class AccountController {

    @ResponseBody
    @RequestMapping(value = "login")
    public Response<UserDto> login(){
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "reset")
    public Response resetPassword(){
        return null;
    }
}
