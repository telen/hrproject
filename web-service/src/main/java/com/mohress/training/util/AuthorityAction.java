package com.mohress.training.util;

import com.mohress.training.entity.security.TblAction;
import com.mohress.training.entity.security.TblAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 权限动作
 */
@Data
@AllArgsConstructor
public class AuthorityAction {

    private TblAuthority authority;

    /**
     * 可为空
     */
    private TblAction action;
}
