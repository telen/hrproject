package com.mohress.training.dto.security;

import lombok.Data;

import java.io.Serializable;

/**
 * 权限
 *
 */
@Data
public class AuthorityDto implements Serializable{

    /**
     * 权限Id
     */
    private String id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 跳转路径
     */
    private String route;

    /**
     * 显示图标
     */
    private String icon;

    /**
     * 父级权限Id
     */
    private String bpid;

    /**
     * 模块权限Id
     */
    private String mpid;
}
