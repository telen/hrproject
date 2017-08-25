package com.mohress.training.entity.ledger;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 台账-毕业生审核快照
 *
 */
@Data
public class TblLedgerGraduateSnapshot {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 台账Id
     */
    private String ledgerId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生Id
     */
    private String studentId;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 人员类型
     */
    private String studentCat;

    /**
     * 缺勤天数
     */
    private Integer absentCount;

    /**
     * 理论成绩
     */
    private BigDecimal theoryScore;

    /**
     * 实践成绩
     */
    private BigDecimal practiceScore;

    /**
     * 是否参保
     * 0-参保
     * 1-未参保
     */
    private Integer insuredStatus;

    /**
     * 结业证书
     */
    private String certificate;

    /**
     * 结业证书编号
     */
    private String certificateId;
}
