package com.mohress.training.service.ledger;

import com.mohress.training.dto.ledger.LedgerApplyDto;

/**
 * 台账服务
 *
 */
public interface LedgerService {

    void apply(LedgerApplyDto ledgerApplyDto);


}
