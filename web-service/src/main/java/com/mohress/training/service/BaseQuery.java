package com.mohress.training.service;

import lombok.Data;

/**
 * 查询体
 * Created by qx.wang on 2017/8/15.
 */
@Data
public class BaseQuery {
    private Integer pageSize;

    private Integer pageIndex;
}
