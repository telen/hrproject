package com.mohress.training.service.course;

import lombok.Data;

/**
 * 教师查询体
 * Created by qx.wang on 2017/8/15.
 */
@Data
public class CourseQuery {

    private String agencyId;

    private String keyword;

    private Integer pageSize;

    private Integer pageIndex;
}
