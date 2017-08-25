package com.mohress.training.dao;

import com.mohress.training.entity.ledger.TblLedgerGraduateSnapshot;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by youtao.wan on 2017/8/24.
 */
public interface TblLedgerGraduateSnapshotDao {

    int insertBatch(List<TblLedgerGraduateSnapshot> ledgerStudentList);

    List<TblLedgerGraduateSnapshot> selectPageByLedgerId(String ledgerId, RowBounds rowBounds);
}
