package com.mohress.training.service.security;

import com.mohress.training.dto.SecurityDto;

import java.util.List;

/**
 *
 * 权限服务
 *
 */
public interface SecurityService {

    List<SecurityDto> queryAuthorityList();

    boolean assignAuthority(String userId, String roleId);

    boolean retrieveAuthority(String userId, String roleId);
}
