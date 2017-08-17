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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    public Response<UserDto> login(HttpServletRequest request){
        /*String account = "root";

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
        return new Response<UserDto>(ResultCode.SUCCESS.getCode(), "用户登录成功", user);*/
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "reset")
    public Response resetPassword(){
        return new Response(ResultCode.SUCCESS.getCode(), "成功");
    }
}
