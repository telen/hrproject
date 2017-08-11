package com.mohress.training.dto;

import com.mohress.training.entity.security.TblAuthority;
import com.mohress.training.entity.security.TblRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * 权限管理-角色权限集合
 *
 */
@Data
@AllArgsConstructor
public class RoleAuthority {

    private TblRole role;

    private Set<TblAuthority> authoritySet;
}
