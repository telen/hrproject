package com.mohress.training.entity.student;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 考试成绩
 *
 */
@Data
public class TblExamScore {

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 考试成绩Id
     */
    private String scoreId;

    /**
     * 学生
     */
    private String studentId;

    /**
     * 班级Id
     */
    private String classId;

    /**
     * 理论成绩
     */
    private BigDecimal theoryScore;

    /**
     * 实践成绩
     */
    private BigDecimal practiceScore;

    /**
     * 结业证书
     */
    private String certificate;

    /**
     * 考试状态
     * 0=考试未通过
     * 1=考试通过
     */
    private Integer examStatus;
}
