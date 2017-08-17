package com.mohress.training.entity.security;

import lombok.Data;

/**
 * 权限管理-账号角色关联实体
 *
 */
@Data
public class TblAccountRole {

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 角色Id
     */
    private String roleId;
}
