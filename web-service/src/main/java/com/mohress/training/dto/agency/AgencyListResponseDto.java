package com.mohress.training.dto.agency;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 相关部门查询返回值
 * Created by qx.wang on 2017/8/15.
 */
@Data
@AllArgsConstructor
public class AgencyListResponseDto implements Serializable {
    private List<AgencyItemDto> agencyItemDtoList;
}
