package com.zhang.trace.master.agent.socket.handler.impl;

import com.zhang.trace.master.agent.socket.handler.ServerRequestHandler;
import com.zhang.trace.master.core.config.socket.request.domain.ConfigUpdatedRequest;
import org.java_websocket.client.WebSocketClient;

/**
 * 收到配置已更新的消息
 *
 * @author zhang
 * @date 2024-10-18 14:57
 */
public class ConfigUpdatedRequestHandler implements ServerRequestHandler<ConfigUpdatedRequest> {

    @Override
    public void handle(ConfigUpdatedRequest data, WebSocketClient session) {

    }

}
