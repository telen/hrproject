package com.mohress.training.service.audit.event;

import com.mohress.training.service.audit.action.InitAction;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 审核流程初始化事件
 *
 */
@Getter
public class AuditInitEvent extends ApplicationEvent {

    private String recordId;

    public AuditInitEvent(String recordId, InitAction initAction) {
        super(initAction);
        this.recordId = recordId;
    }
}
