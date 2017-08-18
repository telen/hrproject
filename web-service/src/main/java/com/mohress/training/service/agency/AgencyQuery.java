package com.mohress.training.service.agency;

import lombok.Data;

/**
 * 机构查询
 * Created by qx.wang on 2017/8/15.
 */
@Data
public class AgencyQuery {
    private Integer pageSize;

    private Integer pageIndex;

    private String agencyId;

    public AgencyQuery(String agencyId) {
        this.agencyId = agencyId;
    }

    public AgencyQuery(Integer pageSize, Integer pageIndex) {

        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }
}
