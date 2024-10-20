package com.zhang.trace.master.agent.socket.handler.impl;

import com.zhang.trace.master.agent.socket.AgentSocketClient;
import com.zhang.trace.master.agent.socket.handler.ServerMessageHandler;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryResultMessage;
import org.java_websocket.client.WebSocketClient;

/**
 * 注册结果消息处理器
 *
 * @author zhang
 * @date 2024-10-18 16:56
 */
public class RegistryResultMessageHandler implements ServerMessageHandler<RegistryResultMessage> {

    @Override
    public void handle(RegistryResultMessage registryResultRequest, WebSocketClient session) {
        AgentSocketClient.setInstanceId(registryResultRequest.getInstanceId());
    }

}
