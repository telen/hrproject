package com.mohress.training.dto.course;

import lombok.Data;

import java.io.Serializable;

/**
 * 课程
 * Created by qx.wang on 2017/8/18.
 */
@Data
public class CourseItemDto implements Serializable {
    /**
     * 课程编号
     */
    private String courseId;

    private String courseName;

    /**
     * 专业
     */
    private String profession;

    private String courseBrief;

    /**
     * 学时
     */
    private Integer period;

    /**
     * 教师名称
     */
    private String teacherName;

    /**
     * 所用教材
     */
    private String teachingMaterial;

    /**
     * 机构名称
     */
    private String agencyName;

    //下面字段传不传均可

    /**
     * 专业描述
     */
    private String professionBrief;

    /**
     * 行业类别
     */
    private String industryCategory;

    /**
     * 培训形式
     */
    private String trainingForm;

    /**
     * 培训工种
     */
    private String trainingJobs;

    private String teacherId;

}
