package com.mohress.training.entity.security;

import lombok.Data;

import java.util.Date;

/**
 * 权限管理-账号实体
 *
 */
@Data
public class TblAccount {

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 用户Id(外键)
     */
    private String userId;

    /**
     * 登录账号(唯一键)
     */
    private String account;

    /**
     * 登录密码(密文)
     */
    private String password;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户手机号(密文)
     */
    private String mobile;

    /**
     * 用户邮箱(密文)
     */
    private String email;

    /**
     * 最新登录Ip
     */
    private String loginIp;

    /**
     * 最近登录时间
     */
    private String loginTime;

    /**
     * 账号是否可用
     */
    private boolean enable;

    /**
     * 账号是否锁定
     */
    private boolean locked;

    /**
     * 账号有效截止日期
     */
    private Date expiredTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
