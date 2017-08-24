package com.mohress.training.service.audit;

import com.mohress.training.service.BaseQuery;

import java.util.List;

/**
 * 审核记录服务
 *
 */
public interface AuditRecordService<T> {

    List<T> queryByPage(BaseQuery baseQuery);
}
