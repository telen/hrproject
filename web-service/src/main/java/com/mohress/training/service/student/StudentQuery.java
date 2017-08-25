package com.mohress.training.service.student;

import lombok.Data;

import java.util.List;

/**
 * 教师查询体
 * Created by qx.wang on 2017/8/15.
 */
@Data
public class StudentQuery {

    private String keyword;

    private Integer pageSize;

    private Integer pageIndex;

    private String courseId;

    private String idNumber;

    private String classId;

    private List<String> studentIds;
}
