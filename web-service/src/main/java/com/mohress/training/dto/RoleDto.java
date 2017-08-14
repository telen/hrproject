package com.mohress.training.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by youtao.wan on 2017/8/11.
 */
@Data
public class RoleDto {

    private String role;

    private List<String> authorityList;
}
