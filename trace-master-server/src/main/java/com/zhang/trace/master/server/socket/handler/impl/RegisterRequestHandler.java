package com.zhang.trace.master.server.socket.handler.impl;

import com.zhang.trace.master.core.config.socket.request.SocketMessage;
import com.zhang.trace.master.core.config.socket.request.SocketMessageType;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryResultMessage;
import com.zhang.trace.master.server.socket.WebSocketSessionManager;
import com.zhang.trace.master.server.socket.handler.AgentRequestHandler;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

/**
 * 注册消息处理器
 *
 * @author zhang
 * @date 2024-10-16 17:24
 */
@Slf4j
public class RegisterRequestHandler implements AgentRequestHandler<RegistryMessage> {

    @Override
    public void handle(RegistryMessage registryRequest, WebSocketSession session) {
        RegistryResultMessage registryResultRequest = new RegistryResultMessage();
        String instanceId = UUID.randomUUID().toString();
        registryResultRequest.setAppId(registryRequest.getAppId());
        registryResultRequest.setInstanceId(instanceId);

        SocketMessage<RegistryResultMessage> serverMessage = new SocketMessage<>(registryResultRequest, SocketMessageType.REGISTRY_RESULT);

        WebSocketSessionManager.saveSession(registryRequest.getAppId(), instanceId, session);
        WebSocketSessionManager.sendMessage(registryRequest.getAppId(), instanceId, serverMessage);
    }

}
