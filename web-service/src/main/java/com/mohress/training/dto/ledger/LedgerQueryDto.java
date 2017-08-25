package com.mohress.training.dto.ledger;

import com.mohress.training.service.BaseQuery;
import lombok.Data;

/**
 * Created by youtao.wan on 2017/8/25.
 */
@Data
public class LedgerQueryDto extends BaseQuery{

    private String agencyId;

    private String classId;
}
