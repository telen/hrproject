package com.mohress.training.dao;

import com.mohress.training.entity.ledger.TblLedger;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 台账审核数据接口
 *
 */
public interface TblLedgerDao {

    int insert(TblLedger tblLedger);

    int updateStatusByLedgerId(@Param("ledgerId") String ledgerId, @Param("status") int status);

    TblLedger selectByLedgerId(String ledgerId);

    List<TblLedger> selectByClassIdAndStatus(String classId, int status);
}
