package com.mohress.training.service.audit.impl;

import com.mohress.training.dao.TblAccountRoleDao;
import com.mohress.training.dao.TblClassAuditRecordDao;
import com.mohress.training.dto.audit.ClassAuditQueryDto;
import com.mohress.training.entity.audit.TblClassAuditRecord;
import com.mohress.training.entity.security.TblAccountRole;
import com.mohress.training.service.BaseQuery;
import com.mohress.training.service.audit.AuditRecordService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课程审核记录查询
 *
 */
@Service
public class ClassAuditRecordServiceImpl implements AuditRecordService<TblClassAuditRecord>{

    @Resource
    private TblClassAuditRecordDao tblClassAuditRecordDao;

    @Resource
    private TblAccountRoleDao tblAccountRoleDao;

    @Override
    public List<TblClassAuditRecord> queryByPage(BaseQuery baseQuery) {
        ClassAuditQueryDto classAuditQueryDto = (ClassAuditQueryDto) baseQuery;

        List<TblAccountRole> tblAccountRoleList = tblAccountRoleDao.selectByUserId(classAuditQueryDto.getUserId());
        classAuditQueryDto.setAuditRoleId(tblAccountRoleList.get(0).getRoleId());
        return tblClassAuditRecordDao.selectPageByAgencyId(classAuditQueryDto.getAgencyId(), classAuditQueryDto.getAuditRoleId(), new RowBounds(baseQuery.getOffset(), baseQuery.getPageSize()));
    }
}
