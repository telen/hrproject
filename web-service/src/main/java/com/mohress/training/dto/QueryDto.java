package com.mohress.training.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询条件
 * Created by qx.wang on 2017/8/16.
 */
@Data
public class QueryDto implements Serializable{
    private String keyword;
    private int page;
    private int pageSize;
}
