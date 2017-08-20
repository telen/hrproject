package com.mohress.training.service.security;

import com.google.common.collect.Lists;
import com.mohress.training.dao.TblAuthorityDao;
import com.mohress.training.dto.SecurityDto;
import com.mohress.training.entity.security.TblAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by youtao.wan on 2017/8/20.
 */
@Service
public class SecurityServiceImpl implements SecurityService{

    @Resource
    private TblAuthorityDao authorityDao;

    @Override
    public List<SecurityDto> queryAuthorityList() {
        List<TblAuthority> authorityList = authorityDao.selectAll();
        List<SecurityDto> securityDtoList = Lists.newArrayList();
        for (TblAuthority authority : authorityList){
            SecurityDto securityDto = new SecurityDto();
            securityDto.setId(authority.getAuthorityId());
            securityDto.setName(authority.getAuthorityName());
            securityDto.setRoute(authority.getAuthorityRoute());
            securityDto.setIcon(authority.getAuthorityIcon());
            securityDto.setBpid(authority.getParentAuthorityId());
            if ("page".equals(authority.getAuthorityType())){
                securityDto.setMpid(authority.getParentAuthorityId());
            }
            securityDtoList.add(securityDto);
        }
        return securityDtoList;
    }

    @Override
    public boolean assignAuthority(String userId, String roleId) {
        return false;
    }

    @Override
    public boolean retrieveAuthority(String userId, String roleId) {
        return false;
    }

}
