package com.mohress.training.controller;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.mohress.training.dto.QueryDto;
import com.mohress.training.dto.Response;
import com.mohress.training.dto.Responses;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.entity.security.TblRole;
import com.mohress.training.service.ModuleBiz;
import com.mohress.training.service.security.AccountManager;
import com.mohress.training.util.AccountAuthority;
import com.mohress.training.util.BusiVerify;
import com.mohress.training.util.CipherUtil;
import com.mohress.training.util.RoleAuthority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
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
    private static final String OFFICIAL_ROLE_ID = "17081610225621055996";
    private static final String ROOT_ROLE_ID = "17081610225621055995";

    @Resource
    private ModuleBiz teacherBizImpl;

    @Resource
    private ModuleBiz agencyBizImpl;

    @Resource
    private ModuleBiz classBizImpl;

    @Resource
    private ModuleBiz studentBizImpl;

    @Resource
    private ModuleBiz courseBizImpl;

    @Resource
    private ModuleBiz attendanceBizImpl;

    @Resource
    private AccountManager accountManager;

    @PostConstruct
    public void init() {
        moduleMap.put("class", classBizImpl);
        moduleMap.put("agency", agencyBizImpl);
        moduleMap.put("teacher", teacherBizImpl);
        moduleMap.put("student", studentBizImpl);
        moduleMap.put("course", courseBizImpl);
        moduleMap.put("attendance", attendanceBizImpl);
    }

    @ResponseBody
    @RequestMapping(value = "new", method = RequestMethod.POST)
    public Response<Boolean> newModule(@PathVariable String module, @CookieValue(name = "token") String encryptedName, @RequestBody String data) {
//    public Response<Boolean> newModule(@PathVariable String module, @RequestBody String data) {
        String userId = CipherUtil.decryptName(encryptedName);
        log.info("userId-{},新建培训机构 {}", userId, data);
        String agencyId = null;
        if (!checkHighAuthority(userId)) {
            TblAgency agency = accountManager.queryAgencyByUserId(userId);
            Preconditions.checkNotNull(agency);
            agencyId = agency.getAgencyId();
        }

        moduleMap.get(module).newModule(data, agencyId);

        log.info("userId-{}, 新建培训机构成功", userId);
        return Responses.succ(Boolean.TRUE);
    }

    @ResponseBody
    @RequestMapping(value = "update")
//    public Response<Boolean> update(@PathVariable String module, @RequestBody String data) {
    public Response<Boolean> update(@PathVariable String module, @CookieValue(name = "token") String encryptedName, @RequestBody String data) {
        String userId = CipherUtil.decryptName(encryptedName);
//        String userId = null;
        log.info("userId-{}, 更新机构 {}", userId, data);

        moduleMap.get(module).update(data);

        log.info("userId-{},更新机构成功");
        return Responses.succ(Boolean.TRUE);
    }


    @ResponseBody
    @RequestMapping(value = "delete")
//    public Response<Boolean> delete(@PathVariable String module, @RequestBody List<String> ids) {
    public Response<Boolean> delete(@PathVariable String module, @CookieValue(name = "token") String encryptedName, @RequestBody List<String> ids) {
        String userId = CipherUtil.decryptName(encryptedName);
//        String userId = null;
        log.info("userId-{}, 删除机构 {}", userId, ids);

        if (!checkHighAuthority(userId)) {
            TblAgency agency = accountManager.queryAgencyByUserId(userId);
            Preconditions.checkNotNull(agency);
            moduleMap.get(module).checkDelete(agency.getAgencyId(), ids);
        }

        moduleMap.get(module).delete(ids);

        log.info("userId-{},删除机构成功");
        return Responses.succ(Boolean.TRUE);
    }

    @ResponseBody
    @RequestMapping(value = "query")
//    public Response<Object> query(@PathVariable String module, QueryDto pageDto) {
    public Response<Object> query(@CookieValue(name = "token") String encryptedName, @PathVariable String module, QueryDto pageDto) {
        if (pageDto == null) {
            pageDto = new QueryDto();
        }
        if (pageDto.getPage() == null || pageDto.getPage() < 0) {
            pageDto.setPage(0);
            pageDto.setPageSize(10);
        } else {
            pageDto.setPage(pageDto.getPage() - 1);
        }
        String userId = CipherUtil.decryptName(encryptedName);

        if (!checkHighAuthority(userId)) {
            TblAgency agency = accountManager.queryAgencyByUserId(userId);
            BusiVerify.verifyNotNull(agency, "用户无归属机构");
            pageDto.setAgencyId(agency.getAgencyId());
        }
//        String userId = null;
        pageDto.setUserId(userId);
        log.info("userId-{}, 查询 {} ,查询条件 {}", userId, module, pageDto);

        Object dto = moduleMap.get(module).query(pageDto);
        log.info("userId-{}, 查询 {}，返回 {}", userId, module, dto);
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
        queryDto.setUserId(userId);
        log.info("userId-{}, 查询 {}, 查询条件 {}", userId, module, queryDto);

        Object dto = moduleMap.get(module).queryByKeyword(queryDto);
        log.info("userId-{}, 查询 {}, 查询条件：{}，返回 {}", userId, module, queryDto, dto);
        return Responses.succ(dto);
    }

    private boolean checkHighAuthority(String userId) {
        AccountAuthority accountAuthority = accountManager.queryAccountAuthorityByUserId(userId);
        BusiVerify.verifyNotNull(accountAuthority, String.format("用户对应账户为空%s", userId));
        BusiVerify.verify(!CollectionUtils.isEmpty(accountAuthority.getAuthorityList()), String.format("用户对应账户角色为空%s", userId));

        List<RoleAuthority> authorityList = accountAuthority.getAuthorityList();
        for (RoleAuthority authority : authorityList) {
            TblRole role = authority.getRole();
            if (role == null) {
                continue;
            }
            if (OFFICIAL_ROLE_ID.equals(role.getRoleId()) || ROOT_ROLE_ID.equals(role.getRoleId())) {
                return true;
            }
        }
        return false;
    }


}
