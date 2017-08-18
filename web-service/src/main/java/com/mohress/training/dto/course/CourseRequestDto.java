package com.mohress.training.dto.course;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程
 * Created by qx.wang on 2017/8/18.
 */
@Data
public class CourseRequestDto implements Serializable {
    private String courseId;

    private String courseName;

    /**
     * 专业
     */
    private String profession;

    /**
     * 学时
     */
    private Integer period;

    /**
     * 一个课程只有一个教室，将其关系存于课程中
     */
    private String teacherId;

    /**
     * 所用教材
     */
    private String teachingMaterial;

    private String courseBrief;

    /**
     * 前端不需要传，后端解析登陆用户
     */
    private String agencyId;

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

}
