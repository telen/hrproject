package com.mohress.training.dao;

import com.mohress.training.entity.ledger.TblLedgerStudent;

import java.util.List;

/**
 * Created by youtao.wan on 2017/8/24.
 */
public interface TblLedgerStudentDao {

    int insertBatch(List<TblLedgerStudent> ledgerStudentList);
}
