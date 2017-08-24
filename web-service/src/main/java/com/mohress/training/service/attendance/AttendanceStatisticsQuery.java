package com.mohress.training.service.attendance;

import lombok.Data;

/**
 *
 * Created by qx.wang on 2017/8/24.
 */
@Data
public class AttendanceStatisticsQuery {
    private Integer pageIndex;

    private Integer pageSize;

    private String agencyId;

    private String agencyName;

    private String classname;

}
