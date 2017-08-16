package com.mohress.training.service.security.handler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mohress.training.cache.AccountAuthorityCache;
import com.mohress.training.dto.Response;
import com.mohress.training.dto.UserDto;
import com.mohress.training.entity.security.TblAuthority;
import com.mohress.training.util.AccountAuthority;
import com.mohress.training.util.RoleAuthority;
import com.mohress.training.util.SerializerFactory;
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
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.mohress.training.enums.ResultCode.SUCCESS;

/**
 * Created by youtao.wan on 2017/8/16.
 */
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private AccountAuthorityCache cache;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (!response.isCommitted()){
            response.setStatus(400);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");

            UserDto userDto = getAccountAuthority(authentication.getName());
            String json = SerializerFactory.defaultSerializer().serialize(new Response<>(SUCCESS.getCode(), "登录成功", userDto));
            Cookie cookie = new Cookie("userToken", userDto.getUserId());

            PrintWriter writer = null;
            try {
                response.addCookie(cookie);

                writer = response.getWriter();
                writer.append(json);
            }catch (IOException ie){
                log.error("登录成功数据写入异常。", ie);
            }finally {
                if (writer != null){
                    writer.flush();
                    writer.close();
                }
            }
        }
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
