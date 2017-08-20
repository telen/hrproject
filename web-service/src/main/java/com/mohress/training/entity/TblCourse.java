package com.mohress.training.entity;

import lombok.Data;

import java.util.Date;

/**
 * 培训课程
 *
 */
@Data
public class TblCourse {
    private Integer id;

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
     * 所用教材
     */
    private String teachingMaterial;

    private String courseBrief;

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

    /**
     * 状态
     * 0-正常
     * 1-已删除
     */
    private Integer status;

    private Date createTime;

    private Date updateTime;

    /**
     * 一个课程只有一个教室，将其关系存于课程中
     */
    private String teacherId;
}
