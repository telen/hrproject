package com.mohress.training.dto.audit;

import com.mohress.training.service.BaseQuery;
import lombok.Data;

/**
 * 班级审核查询DTO
 *
 */
@Data
public class ClassAuditQueryDto extends BaseQuery{

    /**
     * 培训机构Id
     */
    private String agencyId;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 审核角色Id
     */
    private String auditRoleId;
}
