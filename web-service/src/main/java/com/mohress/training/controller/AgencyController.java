package com.mohress.training.controller;

import com.mohress.training.dto.PageDto;
import com.mohress.training.dto.Response;
import com.mohress.training.dto.Responses;
import com.mohress.training.dto.agency.AgencyListResponseDto;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.service.agency.AgencyQuery;
import com.mohress.training.service.agency.AgencyService;
import com.mohress.training.util.CipherUtil;
import com.mohress.training.util.Convert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 培训机构管理，均为培训机构上一级操作
 * <p>
 * Created by youtao.wan on 2017/8/9.
 */
@Slf4j
@Controller
@RequestMapping("api/agency/")
public class AgencyController {

    @Resource
    private AgencyService agencyServiceImpl;

    @ResponseBody
    @RequestMapping(value = "query/{agencyId}")
    public Response<AgencyListResponseDto> queryAgency(@CookieValue(name = "token") String encryptedName, @PathVariable String agencyId, @RequestBody PageDto dto) {
        String userId = CipherUtil.decryptName(encryptedName);
        log.info("userId-{}, 查询机构 {} ,查询条件 {}", userId, agencyId, dto);

        List<TblAgency> tblAgencies = agencyServiceImpl.queryAgencies(buildAgencyQuery(dto));
        //todo 填充教师人数
        AgencyListResponseDto responseDto = Convert.convertAgency(tblAgencies);
        log.info("userId-{}, 查询机构 {}, 返回 {}", userId, agencyId, responseDto);
        return Responses.succ(responseDto);
    }

    private AgencyQuery buildAgencyQuery(PageDto dto) {
        AgencyQuery query = new AgencyQuery();
        query.setPageIndex(dto.getPage());
        query.setPageSize(dto.getPageSize());
        return query;
    }

}
