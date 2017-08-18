package com.mohress.training.controller;

import com.google.common.base.Verify;
import com.mohress.training.dto.Response;
import com.mohress.training.dto.Responses;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.entity.mclass.TblClass;
import com.mohress.training.service.agency.AgencyQuery;
import com.mohress.training.service.agency.AgencyServiceImpl;
import com.mohress.training.service.mclass.ClassQuery;
import com.mohress.training.service.mclass.ClassServiceImpl;
import com.mohress.training.util.CipherUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 培训班级
 * <p>
 * Created by youtao.wan on 2017/8/9.
 */
@Slf4j
@Controller
@RequestMapping("api/class/")
public class ClassController {

//    @Resource
//    private ClassServiceImpl classServiceImpl;

//    @Resource
//    private AgencyServiceImpl agencyServiceImpl;
//
//    @ResponseBody
//    @RequestMapping("apply")
//    public Response<Boolean> applyFor(@CookieValue(name = "token") String encryptedName, String classId) {
//        String userId = CipherUtil.decryptName(encryptedName);
//        List<TblAgency> agencyList = agencyServiceImpl.query(new AgencyQuery(""));
//
//        Verify.verify(!CollectionUtils.isEmpty(agencyList));
//
//        //申请开班1，记录请求；2-修改状态；
//        List<TblClass> tblClass = classServiceImpl.query(new ClassQuery(classId, agencyList.get(0).getAgencyId()));
//        Verify.verify(!CollectionUtils.isEmpty(tblClass));
//        classServiceImpl.alterStatus(classId, TblClass.Status.INIT, TblClass.Status.APPLIED);
//        return Responses.succ(Boolean.TRUE);
//    }
}
