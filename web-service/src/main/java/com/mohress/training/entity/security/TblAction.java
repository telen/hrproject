package com.mohress.training.entity.security;

import lombok.Data;

/**
 * 权限管理-资源操作实体
 *
 */
@Data
public class TblAction {

    /**
     * 主键Id
     *
     */
    private Long id;

    /**
     * 动作Id
     */
    private String actionId;

    /**
     * 动作名称
     */
    private String actionName;

    /**
     * 动作描述
     */
    private String actionDesc;

    /**
     * 资源操作URL
     */
    private String resourcePath;

    /**
     * 关联的权限Id
     */
    private String authorityId;
}
