package com.mohress.training.service.security.handler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mohress.training.cache.AccountAuthorityCache;
import com.mohress.training.dao.TblAccountDao;
import com.mohress.training.dto.Response;
import com.mohress.training.dto.UserDto;
import com.mohress.training.entity.security.TblAuthority;
import com.mohress.training.util.writer.JsonResponseWriter;
import com.mohress.training.util.writer.Writer;
import com.mohress.training.util.AccountAuthority;
import com.mohress.training.util.RoleAuthority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.mohress.training.enums.ResultCode.SUCCESS;

/**
 * 登录成功处理器
 *
 */
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private AccountAuthorityCache cache;

    @Resource
    private TblAccountDao accountDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 1.更新登录时间和登录Ip
        String loginIp = request.getRemoteAddr();
        String userName = authentication.getName();
        Date loginTime = new Date();

        accountDao.updateLogin(userName, loginIp, loginTime);

        // 2.查询用户信息
        UserDto userDto = getAccountAuthority(authentication.getName());

        Cookie cookie = new Cookie("token", userDto.getUserId());
        cookie.setPath("/");
        cookie.setMaxAge(86400);

        // 3.响应数据写入
        Writer writer = new JsonResponseWriter(response);
        writer.write(new Response<>(SUCCESS.getCode(), "登录成功", userDto));
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
