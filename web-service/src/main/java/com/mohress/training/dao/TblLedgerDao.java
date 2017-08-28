package com.mohress.training.dao;

import com.mohress.training.entity.ledger.TblLedger;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.math.RoundingMode;
import java.util.List;

/**
 * 台账审核数据接口
 *
 */
public interface TblLedgerDao {

    int insert(TblLedger tblLedger);

    int updateStatusByLedgerId(@Param("ledgerId") String ledgerId, @Param("status") int status);

    int countByClassIdAndStatus(@Param("classId") String classId, @Param("status")int status);

    TblLedger selectByLedgerId(String ledgerId);

    List<TblLedger> selectPageByAgencyId(String agencyId, RowBounds rowBounds);
}
