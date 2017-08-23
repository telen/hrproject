package com.mohress.training.dto.mclass;

import lombok.Data;

/**
 * 开班申请DTO
 *
 */
@Data
public class ClassApplyDto {

    /**
     * 班级Id
     */
    private String classId;

    /**
     * 申请人Id
     */
    private String applicant;
}
