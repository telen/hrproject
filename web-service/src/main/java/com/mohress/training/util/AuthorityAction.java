package com.mohress.training.util;

import com.mohress.training.entity.security.TblAction;
import com.mohress.training.entity.security.TblAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 权限动作
 */
@Data
@AllArgsConstructor
public class AuthorityAction {

    /**
     * 权限信息
     */
    private TblAuthority authority;

    /**
     * 权限关联动作
     */
    private List<TblAction> actionList;
}
