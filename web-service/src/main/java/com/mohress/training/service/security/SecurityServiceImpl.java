package com.mohress.training.service.security;

import com.google.common.collect.Lists;
import com.mohress.training.dao.TblAuthorityDao;
import com.mohress.training.dto.security.AuthorityDto;
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
    public List<AuthorityDto> queryAuthorityList() {
        List<TblAuthority> authorityList = authorityDao.selectAll();
        List<AuthorityDto> authorityDtoList = Lists.newArrayList();
        for (TblAuthority authority : authorityList){
            AuthorityDto authorityDto = new AuthorityDto();
            authorityDto.setId(authority.getAuthorityId());
            authorityDto.setName(authority.getAuthorityName());
            authorityDto.setRoute(authority.getAuthorityRoute());
            authorityDto.setIcon(authority.getAuthorityIcon());
            authorityDto.setBpid(authority.getParentAuthorityId());
            if ("page".equals(authority.getAuthorityType())){
                authorityDto.setMpid(authority.getParentAuthorityId());
            }
            authorityDtoList.add(authorityDto);
        }
        return authorityDtoList;
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
