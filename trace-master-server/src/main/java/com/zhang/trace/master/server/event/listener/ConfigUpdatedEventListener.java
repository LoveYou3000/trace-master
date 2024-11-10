package com.zhang.trace.master.server.event.listener;

import com.zhang.trace.master.core.socket.request.SocketMessage;
import com.zhang.trace.master.core.socket.request.SocketMessageType;
import com.zhang.trace.master.core.socket.request.domain.ConfigUpdatedMessage;
import com.zhang.trace.master.server.event.ConfigUpdatedEvent;
import com.zhang.trace.master.server.socket.WebSocketSessionManager;
import lombok.NonNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 配置已更新事件监听器
 *
 * @author zhang
 * @date 2024-10-20 19:41
 */
@Component
public class ConfigUpdatedEventListener implements ApplicationListener<ConfigUpdatedEvent> {

    @Override
    public void onApplicationEvent(@NonNull ConfigUpdatedEvent event) {
        ConfigUpdatedMessage configUpdatedMessage = new ConfigUpdatedMessage();
        configUpdatedMessage.setAppId(event.getAppId());
        configUpdatedMessage.setLastUpdate(event.getLastUpdate());

        SocketMessage<ConfigUpdatedMessage> serverMessage = new SocketMessage<>(configUpdatedMessage, SocketMessageType.CONFIG_UPDATED);

        WebSocketSessionManager.broadcastMessage(event.getAppId(), serverMessage);
    }

}
