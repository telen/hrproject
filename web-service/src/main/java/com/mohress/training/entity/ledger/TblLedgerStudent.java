package com.mohress.training.entity.ledger;

import lombok.Data;

/**
 * 台账记录与学生关联实体
 *
 */
@Data
public class TblLedgerStudent {

    private Long id;

    private String ledgerId;

    private String studentId;
}
