package com.mohress.training.dto.ledger;

import lombok.Data;

import java.util.List;

/**
 * 台账申请DTO
 *
 */
@Data
public class LedgerApplyDto {

    /**
     * 申请人
     */
    private String applicant;

    /**
     * 班级Id
     */
    private String classId;

    /**
     * 申请台账的学生Id列表
     */
    private List<String> studentList;
}
