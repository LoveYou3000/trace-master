package com.zhang.trace.master.server.socket.handler.impl;

import com.zhang.trace.master.core.config.socket.request.ServerRequest;
import com.zhang.trace.master.core.config.socket.request.ServerRequestType;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryResultRequest;
import com.zhang.trace.master.server.socket.WebSocketSessionManager;
import com.zhang.trace.master.server.socket.handler.AgentRequestHandler;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

/**
 * 注册消息处理
 *
 * @author zhang
 * @date 2024-10-16 17:24
 */
@Slf4j
public class RegisterRequestHandler implements AgentRequestHandler<RegistryRequest> {

    @Override
    public void handle(RegistryRequest registryRequest, WebSocketSession session) {
        log.info("收到注册请求:" + registryRequest);
        RegistryResultRequest registryResultRequest = new RegistryResultRequest();
        String instanceId = UUID.randomUUID().toString();
        registryResultRequest.setAppId(registryRequest.getAppId());
        registryResultRequest.setInstanceId(instanceId);

        ServerRequest<RegistryResultRequest> serverRequest = new ServerRequest<>();
        serverRequest.setData(registryResultRequest);
        serverRequest.setType(ServerRequestType.REGISTRY_RESULT);

        WebSocketSessionManager.saveSession(registryRequest.getAppId(), instanceId, session);
        WebSocketSessionManager.sendMessage(registryRequest.getAppId(), instanceId, serverRequest);
    }

}
