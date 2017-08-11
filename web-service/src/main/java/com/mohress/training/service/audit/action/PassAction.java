package com.mohress.training.service.audit.action;

import com.mohress.training.service.audit.action.Action;

/**
 * 审核-通过动作
 * 当前步骤处理通过，进入下一步骤，若为末步骤，则流程处理完成；
 *
 * Created by youtao.wan on 2017/8/10.
 */
public interface PassAction extends Action {
}
