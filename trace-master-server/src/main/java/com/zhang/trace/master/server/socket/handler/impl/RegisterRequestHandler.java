package com.zhang.trace.master.server.socket.handler.impl;

import com.zhang.trace.master.server.socket.WebSocketSessionManager;
import com.zhang.trace.master.server.socket.handler.AgentRequestHandler;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryRequest;
import com.zhang.trace.master.core.config.socket.response.domain.RegistryResponse;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

/**
 * 注册消息处理
 *
 * @author zhang
 * @date 2024-10-16 17:24
 */
public class RegisterRequestHandler implements AgentRequestHandler<RegistryRequest> {

    @Override
    public void handle(RegistryRequest registryRequest, WebSocketSession session) {
        RegistryResponse registryResponse = new RegistryResponse();
        String instanceId = UUID.randomUUID().toString();
        registryResponse.setInstanceId(instanceId);

        WebSocketSessionManager.saveSession(registryRequest.getAppId(), instanceId, session);
        WebSocketSessionManager.sendMessage(registryRequest.getAppId(), instanceId, registryResponse);
    }

}
