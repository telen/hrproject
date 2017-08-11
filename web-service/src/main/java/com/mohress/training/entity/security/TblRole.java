package com.mohress.training.entity.security;

import lombok.Data;

import java.util.Date;

/**
 * 权限管理-角色实体
 *
 */
@Data
public class TblRole {

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 角色Id(外键)
     */
    private String roleId;

    /**
     * 角色名称(ROLE_*)
     * 约定角色名称以ROLE_起始
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 角色优先级
     * 值越大优先级越低
     */
    private Integer priority;

    /**
     * 角色是否可用
     */
    private boolean enable;

    /**
     * 父角色Id
     */
    private String parentRoleId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
