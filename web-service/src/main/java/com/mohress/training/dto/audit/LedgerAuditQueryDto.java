package com.mohress.training.dto.audit;

import com.mohress.training.service.BaseQuery;
import lombok.Data;

/**
 * Created by youtao.wan on 2017/8/25.
 */
@Data
public class LedgerAuditQueryDto extends BaseQuery{

    private String agencyId;

    /**
     * 审核角色Id
     */
    private String auditRoleId;
}
