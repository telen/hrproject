package com.mohress.training.service.audit.event;

import com.mohress.training.service.audit.action.RejectAction;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 审核否决事件
 *
 */
@Getter
public class AuditRejectEvent extends ApplicationEvent{

    private String recordId;

    public AuditRejectEvent(String recordId, RejectAction source) {
        super(source);
        this.recordId = recordId;
    }
}
