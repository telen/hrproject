package com.mohress.training.dto.ledger;

/**
 * 台账审核关联的学生信息
 *
 */
public class LedgerStudentItemDto {

    /**
     * 台账记录Id
     */
    private String ledgerId;

    /**
     * 学生Id
     */
    private String studentId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生身份证号
     */
    private String idNumber;

    /**
     * 学员类型
     */
    private String studentCat;

    /**
     * 缺勤天数
     */
    private Integer absentCount;

    /**
     * 理论成绩
     */
    private String theoryScore;

    /**
     * 实践成绩
     */
    private String practiceScore;

    /**
     * 参保情况
     * 0-参保
     * 1-未参保
     */
    private Integer insuredStatus;

    /**
     * 培训证书类型
     */
    private String certificate;
    /**
     * 培训证书等级
     */
    private String certificateLevel;

}
