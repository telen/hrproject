package com.mohress.training.entity.mclass;

import lombok.Data;

import java.util.Date;

/**
 * 培训班级
 */
@Data
public class TblClass {
    private Integer id;

    private String courseId;

    private String classId;

    private String classname;

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
     * 班级上课时间
     */
    private Date onClassTime;

    /**
     * 班级下课时间
     */
    private Date offClassTime;

    /**
     * 申请人
     */
    private String proposer;

    private String proposerMobile;

    private String teachPlanFilePath;

    private Integer status;

    /**
     * 抽查次数
     */
    private Integer inspectionCount;

    /**
     * 抽查是否通过
     */
    private Integer inspectionStatus;

    private Date createTime;

    private Date updateTime;

    private String agencyId;

    public class InspectionStatus {

        public static final int NOT_CHECK = 0;

        public static final int PASSED = 1;

        public static final int NOT_PASS = 2;
    }

    public class Status {
        public static final int INIT = 0;
        /**
         * 已申请
         */
        public static final int APPLIED = 1;

        public static final int ACCESSED = 2;

        public static final int REJECTED = 3;

        public static final int DELETE = 4;
    }
}
