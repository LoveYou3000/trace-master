package com.zhang.trace.master.server.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Spring事件发布 工具类
 *
 * @author zhang
 * @date 2024-10-20 19:39
 */
@Component
public class EventPublisher {

    private static ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        EventPublisher.applicationEventPublisher = applicationEventPublisher;
    }

    public static void publishEvent(ApplicationEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

}
