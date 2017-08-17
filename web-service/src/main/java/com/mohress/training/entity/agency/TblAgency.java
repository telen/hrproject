package com.mohress.training.entity.agency;

import lombok.Data;

import java.util.Date;

/**
 * 机构内部实体
 * Created by qx.wang on 2017/8/15.
 */
@Data
public class TblAgency {
    private Integer id;
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
     * 上级管理部门
     */
    private String superiorDepartment;

    /**
     * 机构状态
     * 0-正常
     * 1-已删除
     */
    private int status;

    private Date createTime;

    private Date updateTime;
}
