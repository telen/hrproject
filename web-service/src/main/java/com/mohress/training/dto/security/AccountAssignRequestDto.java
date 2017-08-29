package com.mohress.training.dto.security;

import lombok.Data;

/**
 *
 * 账号分配请求
 *
 */
@Data
public class AccountAssignRequestDto {

    /**
     * 账号
     */
    private String account;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 账号密码
     */
    private String password;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 机构Id，可为空
     */
    private String agencyId;

    /**
     * 角色Id
     */
    private String roleId;
}
