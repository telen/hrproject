package com.mohress.training.service.mclass;

import lombok.Data;

/**
 * Created by qx.wang on 2017/8/17.
 */
@Data
public class ClassQuery {

    private String classId;

    private String agencyId;

    private Integer pageSize;

    private Integer pageIndex;

    private String classname;

    public ClassQuery(Integer pageSize, Integer pageIndex) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }

}
