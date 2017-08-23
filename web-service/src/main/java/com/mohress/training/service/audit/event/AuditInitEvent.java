package com.mohress.training.service.audit.event;

import com.mohress.training.service.audit.action.InitAction;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * 审核流程初始化事件
 *
 * Created by youtao.wan on 2017/8/23.
 */
@Data
public class AuditInitEvent extends ApplicationEvent {

    private String recordId;

    public AuditInitEvent(String recordId, InitAction initAction) {
        super(initAction);
        this.recordId = recordId;
    }
}
