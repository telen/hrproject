package com.mohress.training.dto.ledger;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by youtao.wan on 2017/8/25.
 */
@Data
public class GraduateSnapshotItemDto implements Serializable{

    /**
     * 台账Id
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
    private String theoryScore;

    /**
     * 实践成绩
     */
    private String practiceScore;

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
