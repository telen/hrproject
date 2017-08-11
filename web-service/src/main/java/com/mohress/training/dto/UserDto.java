package com.mohress.training.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by youtao.wan on 2017/8/11.
 */
@Data
public class UserDto {

    private String userId;

    private String userName;

    private OrganizationDto organization;

    private List<RoleDto> roleList;
}
