package com.mohress.training.controller;

import com.google.common.collect.Maps;
import com.mohress.training.dto.Response;
import com.mohress.training.dto.Responses;
import com.mohress.training.service.ModuleBiz;
import com.mohress.training.util.CipherUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 基本增删改查的入口
 * Created by qx.wang on 2017/8/15.
 */
@Slf4j
@Controller
@RequestMapping("api/{module}")
public class BaseManageController {

    private static final Map<String, ModuleBiz> moduleMap = Maps.newHashMap();
    @Resource
    private ModuleBiz teacherBizImpl;

    @Resource
    private ModuleBiz agencyBizImpl;

    @PostConstruct
    public void init() {
        moduleMap.put("agency", agencyBizImpl);
        moduleMap.put("teacher", teacherBizImpl);
    }

    @ResponseBody
    @RequestMapping(value = "new")
    public Response<Boolean> newModule(@PathVariable String module, @CookieValue(name = "token") String encryptedName, String dto) {
        String userId = CipherUtil.decryptName(encryptedName);
        log.info("userId-{},新建培训机构 {}", userId, dto);

        moduleMap.get(module).newModule(dto);

        log.info("userId-{}, 新建培训机构成功", userId);
        return Responses.succ(Boolean.TRUE);
    }

    @ResponseBody
    @RequestMapping(value = "update")
    public Response<Boolean> update(@PathVariable String module, @CookieValue(name = "token") String encryptedName, String dto) {
        String userId = CipherUtil.decryptName(encryptedName);
        log.info("userId-{}, 更新机构 {}", userId, dto);

        moduleMap.get(module).update(dto);

        log.info("userId-{},更新机构成功");
        return Responses.succ(Boolean.TRUE);
    }


    @ResponseBody
    @RequestMapping(value = "delete")
    public Response<Boolean> delete(@PathVariable String module, @CookieValue(name = "token") String encryptedName, List<String> ids) {
        String userId = CipherUtil.decryptName(encryptedName);
        log.info("userId-{}, 删除机构 {}", userId, ids);

        moduleMap.get(module).delete(ids);

        log.info("userId-{},删除机构成功");
        return Responses.succ(Boolean.TRUE);
    }

}
