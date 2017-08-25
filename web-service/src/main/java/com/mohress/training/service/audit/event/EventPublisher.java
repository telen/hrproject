package com.mohress.training.service.audit.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.mohress.training.enums.ResultCode;
import com.mohress.training.exception.BusinessException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 事件发布者
 *
 */
@Setter
@Slf4j
public class EventPublisher implements Publisher{

    private EventBus eventBus = new EventBus("audit");

    private List<Subscriber> subscribers;

    @PostConstruct
    public void init(){
        for (Subscriber subscriber: subscribers){
            eventBus.register(subscriber);
        }
    }

    @Override
    public void push(ApplicationEvent event) {
        eventBus.post(event);
    }
}
