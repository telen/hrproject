package com.mohress.training.dto.security;

import lombok.Data;

import java.io.Serializable;

/**
 * 账号列表
 */
@Data
public class AccountDetailDto implements Serializable{

    private String userId;

    private String account;

    private String userName;

    private String mobile;

    private String email;

    private String roleId;

    private String roleName;
}
