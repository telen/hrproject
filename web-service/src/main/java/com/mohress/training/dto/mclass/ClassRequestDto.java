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
    private String courseId;

    private String className;

    /**
     * 所属专业
     */
    private String affiliatedProfession;

    /**
     * 预计人数
     */
    private Integer predictedCount;

    private Date startTime;

    private Date endTime;

    /**
     * 申请人
     */
    private String proposer;

    private String proposerMobile;

    private String teachPlanFilePath;

    private List<String> studentIds;
}
