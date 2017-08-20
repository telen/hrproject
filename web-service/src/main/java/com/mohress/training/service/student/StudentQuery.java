package com.mohress.training.service.student;

import lombok.Data;

/**
 * 教师查询体
 * Created by qx.wang on 2017/8/15.
 */
@Data
public class StudentQuery {

    private String agencyId;

    private String keyword;

    private Integer pageSize;

    private Integer pageIndex;
}