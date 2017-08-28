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
     * 结业证书Id
     */
    private String certificateId;
}
