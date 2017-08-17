package com.mohress.training.dto.teacher;

import lombok.Data;

import java.io.Serializable;

/**
 * 教师列表中单个教师
 * Created by qx.wang on 2017/8/15.
 */
@Data
public class TeacherItemDto implements Serializable {
    private String teacherId;

    private String idNumber;

    private String name;

    private String mobile;

    private int gender;

    private String nationality;

    private String birthday;

    private String affiliatedInstitution;

}
