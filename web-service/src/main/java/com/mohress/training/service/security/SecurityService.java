package com.mohress.training.service.security;

import com.mohress.training.dto.security.AccountAssignRequestDto;
import com.mohress.training.dto.security.AccountDetailDto;
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
     * 账号详情
     *
     * @param userId
     * @return
     */
    List<AccountDetailDto> queryAccountList(String userId);

    /**
     * 分配账号
     *
     * @param userId
     * @param accountAssignRequestDto
     */
    void assignAccount(String userId, AccountAssignRequestDto accountAssignRequestDto);
}
