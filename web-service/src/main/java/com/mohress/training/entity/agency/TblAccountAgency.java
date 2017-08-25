package com.mohress.training.entity.agency;

import lombok.Data;

/**
 * 账号与培训机构关联实体
 *
 */
@Data
public class TblAccountAgency {

    private Long id;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 培训机构Id
     */
    private String agencyId;
}
