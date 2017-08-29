package com.mohress.training.service.security;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mohress.training.cache.AccountAuthorityCache;
import com.mohress.training.dao.*;
import com.mohress.training.dto.security.AccountAssignRequestDto;
import com.mohress.training.dto.security.AccountDetailDto;
import com.mohress.training.dto.security.AuthorityDto;
import com.mohress.training.entity.agency.TblAccountAgency;
import com.mohress.training.entity.agency.TblAgency;
import com.mohress.training.entity.security.TblAccount;
import com.mohress.training.entity.security.TblAccountRole;
import com.mohress.training.entity.security.TblAuthority;
import com.mohress.training.entity.security.TblRole;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;
import com.mohress.training.util.AccountAuthority;
import com.mohress.training.util.DateUtil;
import com.mohress.training.util.SequenceCreator;
import com.mohress.training.util.constant.RoleConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.management.relation.Role;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 账号权限服务
 *
 */
@Service
public class SecurityServiceImpl implements SecurityService{

    @Resource
    private TblRoleDao tblRoleDao;

    @Resource
    private TblAgencyDao tblAgencyDao;

    @Resource
    private TblAccountDao tblAccountDao;

    @Resource
    private TblAuthorityDao authorityDao;

    @Resource
    private TblAccountRoleDao tblAccountRoleDao;

    @Resource
    private TblAccountAgencyDao tblAccountAgencyDao;

    @Resource
    private AccountAuthorityCache cache;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<AuthorityDto> queryAuthorityList() {
        List<TblAuthority> authorityList = authorityDao.selectAll();

        List<AuthorityDto> authorityDtoList = Lists.newArrayList();

        for (TblAuthority authority : authorityList){
            authorityDtoList.add(toAuthorityDto(authority));
        }
        return authorityDtoList;
    }

    @Override
    public List<AccountDetailDto> queryAccountList(String userId) {
        TblRole tblRole = getHighestPriorityRole(userId);

        List<TblRole> tblRoleList = tblRoleDao.selectAtLeastPriority(tblRole.getPriority());

        List<AccountDetailDto> accountDetailDtoList = Lists.newArrayList();
        for (TblRole role: tblRoleList){
            List<TblAccount> tblAccountList = tblAccountDao.selectByRoleId(role.getRoleId());
            for (TblAccount it: tblAccountList){
                accountDetailDtoList.add(toAccountDetailDto(it));
            }
        }

        return accountDetailDtoList;
    }

    @Override
    @Transactional
    public void assignAccount(String userId, AccountAssignRequestDto accountAssignRequestDto) {
        TblRole tblRole = getHighestPriorityRole(userId);

        TblRole waitAssignRole = tblRoleDao.selectByRoleId(accountAssignRequestDto.getRoleId());
        if (tblRole.getPriority() > waitAssignRole.getPriority()){
            throw new BusinessException(ResultCode.FAIL, String.format("%s无权分配%s的账号权限", tblRole.getRoleName(), waitAssignRole.getRoleName()));
        }

        TblAccount tblAccount = new TblAccount();
        BeanUtils.copyProperties(accountAssignRequestDto, tblAccount);

        tblAccount.setPassword(passwordEncoder.encode(tblAccount.getPassword()));
        tblAccount.setUserId(SequenceCreator.getUserId());
        tblAccount.setLocked(false);
        tblAccount.setEnable(true);
        tblAccount.setExpiredTime(DateUtil.addYear(new Date(), 2));

        newAccount(tblAccount, accountAssignRequestDto);
    }

    private void newAccount(TblAccount tblAccount, AccountAssignRequestDto accountAssignRequestDto){

        try {
            tblAccountDao.insert(tblAccount);
        }catch (DuplicateKeyException e){
            throw new BusinessException(ResultCode.FAIL, "账号名已被注册");
        }

        TblAccountRole tblAccountRole = new TblAccountRole();
        tblAccountRole.setUserId(tblAccount.getUserId());
        tblAccountRole.setRoleId(accountAssignRequestDto.getRoleId());

        tblAccountRoleDao.insert(tblAccountRole);

        if (!Strings.isNullOrEmpty(accountAssignRequestDto.getAgencyId()) && RoleConstant.ROOT_ROLE_ID.equals(accountAssignRequestDto.getRoleId())){
            TblAgency tblAgency = tblAgencyDao.selectByAgencyId(accountAssignRequestDto.getAgencyId());
            if (tblAgency == null){
                throw new BusinessException(ResultCode.FAIL, "关联培训机构失败，培训机构不存在");
            }

            TblAccountAgency tblAccountAgency = new TblAccountAgency();
            tblAccountAgency.setUserId(tblAccount.getUserId());
            tblAccountAgency.setAgencyId(accountAssignRequestDto.getAgencyId());
            tblAccountAgencyDao.insert(tblAccountAgency);
        }
    }

    private TblRole getHighestPriorityRole(String userId){
        List<TblAccountRole> tblAccountRoleList = tblAccountRoleDao.selectByUserId(userId);

        List<TblRole> tblRoleList = Lists.newArrayList();
        for (TblAccountRole it: tblAccountRoleList){
            TblRole tblRole = tblRoleDao.selectByRoleId(it.getRoleId());
            tblRoleList.add(tblRole);
        }

        Collections.sort(tblRoleList, new Comparator<TblRole>() {
            @Override
            public int compare(TblRole o1, TblRole o2) {
                return o1.getPriority() - o2.getPriority();
            }
        });

        return tblRoleList.get(0);
    }

    private AuthorityDto toAuthorityDto(TblAuthority authority){
        AuthorityDto authorityDto = new AuthorityDto();

        authorityDto.setId(authority.getAuthorityId());
        authorityDto.setName(authority.getAuthorityName());
        authorityDto.setRoute(authority.getAuthorityRoute());
        authorityDto.setIcon(authority.getAuthorityIcon());
        authorityDto.setBpid(authority.getParentAuthorityId());
        if ("page".equals(authority.getAuthorityType())){
            authorityDto.setMpid(authority.getParentAuthorityId());
        }
        return authorityDto;
    }

    private AccountDetailDto toAccountDetailDto(TblAccount tblAccount){
        AccountAuthority accountAuthority = cache.getUnchecked(tblAccount.getAccount());

        AccountDetailDto accountDetailDto = new AccountDetailDto();
        BeanUtils.copyProperties(tblAccount, accountDetailDto);
        BeanUtils.copyProperties(accountAuthority.getAuthorityList().get(0).getRole(), accountDetailDto);
        return accountDetailDto;
    }
}
