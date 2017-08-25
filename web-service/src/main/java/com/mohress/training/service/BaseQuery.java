package com.mohress.training.service;


/**
 * 查询体
 * Created by qx.wang on 2017/8/15.
 */
public class BaseQuery {
    private Integer pageSize;

    private Integer pageIndex;

    public Integer getPageSize() {
        return pageSize == null || pageSize < 0 ? 10 : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex == null || pageIndex < 1 ? 1 : pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getOffset(){
        return (getPageIndex() - 1) * getPageSize();
    }
}
