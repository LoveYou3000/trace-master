package com.zhang.trace.master.agent.socket.handler.impl;

import com.zhang.trace.master.agent.interceptor.context.TraceMasterContext;
import com.zhang.trace.master.agent.socket.AgentSocketClient;
import com.zhang.trace.master.agent.socket.handler.ServerMessageHandler;
import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
import com.zhang.trace.master.core.socket.request.domain.ConfigUpdatedMessage;

/**
 * 配置已更新消息处理器
 *
 * @author zhang
 * @date 2024-10-18 14:57
 */
public class ConfigUpdatedMessageHandler implements ServerMessageHandler<ConfigUpdatedMessage> {

    @Override
    public void handle(ConfigUpdatedMessage configUpdatedMessage, AgentSocketClient session) {
        TraceMasterAgentConfig config = TraceMasterContext.getTraceMasterAgentConfig();
        if (config.getLastUpdate() < configUpdatedMessage.getLastUpdate()) {
            session.fetchConfig();
        }
    }

}
