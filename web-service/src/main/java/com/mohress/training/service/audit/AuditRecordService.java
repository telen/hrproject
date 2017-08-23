package com.mohress.training.service.audit;

import com.mohress.training.service.BaseQuery;

import java.util.List;

/**
 * Created by youtao.wan on 2017/8/23.
 */
public interface AuditRecordService<T> {

    List<T> queryByPage(BaseQuery baseQuery);
}
