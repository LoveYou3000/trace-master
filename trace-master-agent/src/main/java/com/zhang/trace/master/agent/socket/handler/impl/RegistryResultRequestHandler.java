package com.zhang.trace.master.agent.socket.handler.impl;

import com.zhang.trace.master.agent.socket.AgentSocketClient;
import com.zhang.trace.master.agent.socket.handler.ServerRequestHandler;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryResultRequest;
import org.java_websocket.client.WebSocketClient;

/**
 * 收到注册结果的消息
 *
 * @author zhang
 * @date 2024-10-18 16:56
 */
public class RegistryResultRequestHandler implements ServerRequestHandler<RegistryResultRequest> {

    @Override
    public void handle(RegistryResultRequest registryResultRequest, WebSocketClient session) {
        AgentSocketClient.setInstanceId(registryResultRequest.getInstanceId());
    }

}
