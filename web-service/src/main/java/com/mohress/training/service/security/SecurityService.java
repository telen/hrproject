package com.mohress.training.service.security;

import com.mohress.training.dto.security.AuthorityDto;

import java.util.List;

/**
 *
 * 权限服务
 *
 */
public interface SecurityService {

    /**
     * 查询权限信息
     *
     * @return
     */
    List<AuthorityDto> queryAuthorityList();

    /**
     * 分配权限
     *
     * @param userId
     * @param roleId
     * @return
     */
    boolean assignAuthority(String userId, String roleId);

    /**
     * 收回权限
     *
     * @param userId
     * @param roleId
     * @return
     */
    boolean retrieveAuthority(String userId, String roleId);
}
