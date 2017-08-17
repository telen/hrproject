package com.mohress.training.entity.security;

import lombok.Data;

/**
 * 权限管理-角色权限关联实体
 *
 * Created by youtao.wan on 2017/8/10.
 */
@Data
public class TblRoleAuthority {

    private Long id;

    private String roleId;

    private String authorityId;
}
