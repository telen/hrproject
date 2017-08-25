package com.mohress.training.util.constant;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * Created by youtao.wan on 2017/8/24.
 */
public final class AuditConstant {

    public static final String CLASS_AUDIT_TEMPLATE_ID = "Class_audit_template";

    public static final String LEDGER_AUDIT_TEMPLATE_ID = "Ledger_audit_template";

    public static final String SEPARATOR = ",";

    public static final Joiner JOINER = Joiner.on(SEPARATOR);

    public static final Splitter SPLITTER = Splitter.on(SEPARATOR).trimResults();
}
