package com.mohress.training.controller;

import com.google.common.collect.Maps;
import com.mohress.training.dto.PageDto;
import com.mohress.training.dto.QueryDto;
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
public class BaseManageController extends BaseController {

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
    @RequestMapping(value = "new", method = RequestMethod.POST)
//    public Response<Boolean> newModule(@PathVariable String module, @CookieValue(name = "token") String encryptedName, String data) {
    public Response<Boolean> newModule(@PathVariable String module, @RequestBody String data) {
//        String userId = CipherUtil.decryptName(encryptedName);
//        log.info("userId-{},新建培训机构 {}", userId, data);

        moduleMap.get(module).newModule(data);

//        log.info("userId-{}, 新建培训机构成功", userId);
        return Responses.succ(Boolean.TRUE);
    }

    @ResponseBody
    @RequestMapping(value = "update")
    public Response<Boolean> update(@PathVariable String module, @RequestBody String data) {
//    public Response<Boolean> update(@PathVariable String module, @CookieValue(name = "token") String encryptedName, String data) {
//        String userId = CipherUtil.decryptName(encryptedName);
        String userId = null;
        log.info("userId-{}, 更新机构 {}", userId, data);

        moduleMap.get(module).update(data);

        log.info("userId-{},更新机构成功");
        return Responses.succ(Boolean.TRUE);
    }


    @ResponseBody
    @RequestMapping(value = "delete")
    public Response<Boolean> delete(@PathVariable String module, List<String> ids) {
//    public Response<Boolean> delete(@PathVariable String module, @CookieValue(name = "token") String encryptedName, List<String> ids) {
//        String userId = CipherUtil.decryptName(encryptedName);
        String userId = null;
        log.info("userId-{}, 删除机构 {}", userId, ids);

        moduleMap.get(module).delete(ids);

        log.info("userId-{},删除机构成功");
        return Responses.succ(Boolean.TRUE);
    }

    @ResponseBody
    @RequestMapping(value = "query/{busiId}")
    public Response<Object> query(@CookieValue(name = "token") String encryptedName, @PathVariable String module, @PathVariable String busiId, @RequestBody PageDto pageDto) {
        String userId = CipherUtil.decryptName(encryptedName);
        log.info("userId-{}, 查询 {} ,查询ID：{}，查询条件 {}", userId, module, busiId, pageDto);

        Object dto = moduleMap.get(module).query(pageDto);
        log.info("userId-{}, 查询 {}, 查询ID：{}，返回 {}", userId, module, busiId, dto);
        return Responses.succ(dto);
    }

    /**
     * 根据关键字查询列表
     */
    @ResponseBody
    @RequestMapping(value = "queryByAgency")
    public Response<Object> queryByAgency(@CookieValue(name = "token") String encryptedName,
                                          @PathVariable String module, @RequestBody QueryDto queryDto) {
        String userId = CipherUtil.decryptName(encryptedName);
        log.info("userId-{}, 查询 {}, 查询条件 {}", userId, module, queryDto);

        Object dto = moduleMap.get(module).queryByKeyword(queryDto);
        log.info("userId-{}, 查询 {}, 查询条件：{}，返回 {}", userId, module, queryDto, dto);
        return Responses.succ(dto);
    }


}