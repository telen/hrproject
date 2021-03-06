package com.mohress.training.enums;

/**
 * 审核状态
 *
 * Created by youtao.wan on 2017/8/13.
 */
public enum AuditStatus {
    AUDIT_WAIT(1),

    AUDIT_PASS(2),

    AUDIT_REJECT(3);

    private int status;

    AuditStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
