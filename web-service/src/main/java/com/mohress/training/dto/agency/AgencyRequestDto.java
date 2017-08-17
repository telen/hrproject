package com.mohress.training.dto.agency;

import lombok.Data;

import java.io.Serializable;

/**
 * 机构请求体
 * Created by qx.wang on 2017/8/15.
 */
@Data
public class AgencyRequestDto implements Serializable {
    /**
     * 机构编码
     */
    private String agencyId;

    private String agencyName;

    /**
     * 负责人
     */
    private String agencyHead;

    /**
     * 负责人联系方式
     */
    private String agencyMobile;

    private String agencyMail;

    /**
     * 机构地址
     */
    private String address;

    /**
     * 教职工数量
     */
    private Integer employeesCount;

    /**
     * 上级管理部门
     */
    private String superiorDepartment;
}
