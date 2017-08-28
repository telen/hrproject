package com.mohress.training.service.ledger;

import com.mohress.training.dto.ledger.*;

import java.util.List;

/**
 * 台账服务
 *
 */
public interface LedgerService {

    void apply(LedgerApplyDto ledgerApplyDto);

    List<LedgerItemDto> queryLedger(LedgerQueryDto ledgerQueryDto);

    List<GraduateSnapshotItemDto> queryLedgerGraduateSnapshot(GraduateSnapshotQueryDto graduateSnapshotQueryDto);
}
