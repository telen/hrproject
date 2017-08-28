package com.mohress.training.service.attendance;

import lombok.Data;

/**
 *
 * Created by qx.wang on 2017/8/20.
 */
@Data
public class AttendanceQuery {

    /**
     * 查询者
     */
    private String userId;
    private String keyword;
    private Integer pageIndex;
    private Integer pageSize;

    private String classId;

    private String courseId;

    private String agencyId;

    public AttendanceQuery (Integer pageIndex,Integer pageSize){
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
