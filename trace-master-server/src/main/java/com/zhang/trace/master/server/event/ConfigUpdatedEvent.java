package com.zhang.trace.master.server.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Instant;

/**
 * 配置已更新事件
 *
 * @author zhang
 * @date 2024-10-20 19:37
 */
@Getter
public class ConfigUpdatedEvent extends ApplicationEvent {

    private final String appId;

    private final Long lastUpdate;

    public ConfigUpdatedEvent(Object source, String appId) {
        super(source);
        this.appId = appId;
        this.lastUpdate = Instant.now().toEpochMilli();
    }

}
