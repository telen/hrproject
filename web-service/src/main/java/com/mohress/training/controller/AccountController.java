package com.mohress.training.controller;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mohress.training.cache.AccountAuthorityCache;
import com.mohress.training.dto.Response;
import com.mohress.training.dto.UserDto;
import com.mohress.training.entity.security.TblAuthority;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.util.AccountAuthority;
import com.mohress.training.util.RoleAuthority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    @RequestMapping(value = "user")
    public Response<UserDto> login(@CookieValue("token") String userId){
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "login")
    public Response login(HttpServletRequest request, HttpServletResponse response){

        // 1.获取账号名
        String account = (String) request.getAttribute("userName");

        // 2.查询用户信息
        UserDto userDto = getAccountAuthority(account);

        // 3.添加cookie
        Cookie cookie = new Cookie("token", userDto.getUserId());
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        response.addCookie(cookie);

        // 4.数据返回
        return new Response<>(ResultCode.SUCCESS.getCode(), "用户登录成功", userDto);
    }

    @ResponseBody
    @RequestMapping(value = "logout")
    public Response logout(){
        return new Response(ResultCode.SUCCESS.getCode(), "退出登录成功");
    }

    @ResponseBody
    @RequestMapping(value = "reset")
    public Response resetPassword(){
        return new Response(ResultCode.SUCCESS.getCode(), "成功");
    }


    private UserDto getAccountAuthority(String account){
        // 1.缓存获取用户及权限信息
        AccountAuthority accountAuthority = cache.getUnchecked(account);

        // 2.数据组装
        UserDto user = new UserDto();

        if (CollectionUtils.isEmpty(accountAuthority.getAuthoritySet())){
            user.setRole("访客");
            user.setAuthorityList(ImmutableList.<String>of());
        }else {
            List<RoleAuthority> roleAuthorityList = Lists.newArrayList(accountAuthority.getAuthoritySet());
            Collections.sort(roleAuthorityList, new Comparator<RoleAuthority>() {
                public int compare(RoleAuthority o1, RoleAuthority o2) {
                    return o2.getRole().getPriority() - o1.getRole().getPriority();
                }
            });

            List<String> authorityList = Lists.newArrayList();
            RoleAuthority roleAuthority = roleAuthorityList.get(0);
            for (TblAuthority authority : roleAuthority.getAuthoritySet()){
                authorityList.add(authority.getAuthorityId());
            }
            user.setAuthorityList(authorityList);
            user.setRole(roleAuthority.getRole().getRoleName());
        }

        user.setUserId(accountAuthority.getAccount().getUserId());
        user.setUserName(accountAuthority.getAccount().getUserName());

        return user;
    }
}
