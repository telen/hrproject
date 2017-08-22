package com.mohress.training.dao;

import com.mohress.training.entity.audit.TblAuditTemplate;

/**
 * Created by youtao.wan on 2017/8/11.
 */
public interface TblAuditTemplateDao {

    TblAuditTemplate selectByTemplateId(String templateId);
}
