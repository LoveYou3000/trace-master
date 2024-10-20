package com.zhang.trace.master.agent.socket.handler.impl;

import com.zhang.trace.master.agent.socket.handler.ServerMessageHandler;
import com.zhang.trace.master.core.config.socket.request.domain.ConfigUpdatedMessage;
import com.zhang.trace.master.core.config.socket.request.domain.FetchConfigMessage;
import org.java_websocket.client.WebSocketClient;

/**
 * 配置已更新消息处理器
 *
 * @author zhang
 * @date 2024-10-18 14:57
 */
public class ConfigUpdatedMessageHandler implements ServerMessageHandler<ConfigUpdatedMessage> {

    @Override
    public void handle(ConfigUpdatedMessage data, WebSocketClient session) {
        FetchConfigMessage fetchConfigRequest = new FetchConfigMessage();
        // TODO 添加 appId 以及 instanceId
        fetchConfigRequest.setAppId("");
        fetchConfigRequest.setInstanceId("");
    }

}
