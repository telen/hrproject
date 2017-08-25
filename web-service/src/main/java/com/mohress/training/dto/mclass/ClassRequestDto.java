package com.mohress.training.dto.mclass;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 机构请求体
 * Created by qx.wang on 2017/8/15.
 */
@Data
public class ClassRequestDto implements Serializable {

    private String classId;

    private String courseId;

    private String classname;

    /**
     * 所属专业
     */
    private String affiliatedProfession;

    /**
     * 预计人数
     */
    private Integer predictedCount;

    private Long startTime;

    private Long endTime;

    /**
     * 班级上课时间
     */
    private Long onClassTime;

    /**
     * 班级下课时间
     */
    private Long offClassTime;

    /**
     * 抽查状态
     */
    private Integer inspectionStatus;

    /**
     * 申请人
     */
    private String proposer;

    private String proposerMobile;

    private String teachPlanFilePath;

    private List<String> studentIds;
}
