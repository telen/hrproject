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
    private Integer page;
    private Integer pageSize;

    private String classId;

    private String sourseId;

    public AttendanceQuery (Integer page,Integer pageSize){
        this.page = page;
        this.pageSize = pageSize;
    }
}
