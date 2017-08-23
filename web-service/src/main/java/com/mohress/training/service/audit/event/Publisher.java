package com.mohress.training.service.audit.event;

import org.springframework.context.ApplicationEvent;

/**
 * 事件发布
 */
public interface Publisher {

    /**
     * 发布事件
     *
     * @param event
     */
    void push(ApplicationEvent event);
}
