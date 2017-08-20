package com.mohress.training.service;

import java.util.List;

/**
 * 基础服务类
 * Created by qx.wang on 2017/8/17.
 */
public interface BaseManageService {

    /**
     * 新增
     */
    <T> void newModule(T t);

    /**
     * 批量删除
     */
    void delete(List<String> ids);

    /**
     * 更新
     */
    <T> void update(T t);

    /**
     * 查询
     */
    <T, M> List<T> query(M m);

    <T, M> List<T> queryByKeyword(M m);
}
