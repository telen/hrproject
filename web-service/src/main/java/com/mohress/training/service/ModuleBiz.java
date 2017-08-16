package com.mohress.training.service;

import java.util.List;

/**
 * 基础增删改查服务
 * Created by qx.wang on 2017/8/15.
 */
public interface ModuleBiz {
    /**
     * 新增
     */
    void newModule(Object o);

    /**
     * 批量删除
     */
    void delete(List<String> ids);

    /**
     * 更新
     */
    void update(Object o);

}
