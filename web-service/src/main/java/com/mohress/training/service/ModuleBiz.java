package com.mohress.training.service;

import com.mohress.training.dto.QueryDto;

import java.util.List;

/**
 * 基础增删改查服务
 * Created by qx.wang on 2017/8/15.
 */
public interface ModuleBiz {
    /**
     * 新增
     */
    void newModule(String o);

    /**
     * 批量删除
     */
    void delete(List<String> ids);

    /**
     * 更新
     */
    void update(String o);

    Object query(QueryDto pageDto);

    Object queryByKeyword(QueryDto queryDto);
}
