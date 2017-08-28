package com.mohress.training.dto.ledger;

import com.mohress.training.service.BaseQuery;
import lombok.Data;

/**
 * 毕业生台账快照查询DTO
 *
 */
@Data
public class GraduateSnapshotQueryDto extends BaseQuery{

    private String ledgerId;
}
