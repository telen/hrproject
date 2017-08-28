package com.mohress.training.dto.student;

import lombok.Data;

/**
 * 结业学生DTO
 *
 */
@Data
public class GraduateDto {

    /**
     * 学生Id
     */
    private String studentId;

    /**
     * 理论成绩
     */
    private String theoryScore;

    /**
     * 实践成绩
     */
    private String practiceScore;

    /**
     * 结业证书
     */
    private String certificate;
}
