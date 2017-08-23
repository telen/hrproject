package com.mohress.training.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询条件
 * Created by qx.wang on 2017/8/16.
 */
@Data
public class QueryDto implements Serializable{
    /**
     * 查询者
     */
    private String userId;
    private String courseId;
    private String classId;
    private String keyword;
    private Integer page;
    private Integer pageSize;

    private String name;

}
