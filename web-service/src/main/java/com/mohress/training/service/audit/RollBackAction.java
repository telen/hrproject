package com.mohress.training.service.audit;

/**
 * 审核-回退动作
 * 将步骤退回至上一步骤，即返回至上一处理人处，若为首步骤，则不进行退回；
 *
 * Created by youtao.wan on 2017/8/10.
 */
public interface RollBackAction extends Action{
}
