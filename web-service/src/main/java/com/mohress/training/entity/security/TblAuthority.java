package com.mohress.training.entity.security;

import lombok.Data;

import java.util.Date;

/**
 * 权限管理-权限实体
 *
 */
@Data
public class TblAuthority {

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 权限Id
     */
    private String authorityId;

    /**
     * 权限名称
     */
    private String authorityName;

    /**
     * 权限描述
     */
    private String authorityDesc;

    /**
     * 资源路径
     */
    private String resourcePath;

    /**
     * 权限开关
     */
    private boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
