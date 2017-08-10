package com.mohress.training.dto;

import com.mohress.training.entity.security.TblAccount;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * 权限管理-账号权限集合
 *
 */
@Data
@AllArgsConstructor
public class AccountAuthority {

    private TblAccount account;

    private Set<RoleAuthority> authoritySet;
}
