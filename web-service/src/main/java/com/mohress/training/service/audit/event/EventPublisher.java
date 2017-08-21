package com.mohress.training.service.audit.event;

import com.google.common.eventbus.EventBus;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 事件发布者
 *
 */
@Setter
public class EventPublisher implements Publisher{

    private EventBus eventBus;

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
