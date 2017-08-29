package com.mohress.training.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询条件
 * Created by qx.wang on 2017/8/16.
 */
@Data
public class QueryDto implements Serializable {
    /**
     * 查询者
     */
    private String userId;
    private String courseId;
    private String keyword;
    private Integer page = 0;
    private Integer pageSize = 10;

    /**
     * 班级名称
     */
    private String classname;

    /**
     * 教师名称
     */
    private String name;

    /**
     * 传递至service的操作者ID
     */
    private String agencyId;

    /**
     * 0-开班检查
     * 1-过程检查
     * 2-结业检查
     */
    private Integer stage;

    /**
     * 根据班级ID查询学生
     */
    private String classId;

}
