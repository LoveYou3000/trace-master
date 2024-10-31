package com.zhang.trace.master.agent.socket.handler.impl;

import com.zhang.trace.master.agent.interceptor.context.TraceMasterContext;
import com.zhang.trace.master.agent.socket.AgentSocketClient;
import com.zhang.trace.master.agent.socket.handler.ServerMessageHandler;
import com.zhang.trace.master.core.config.socket.request.domain.AgentEnableMessage;

/**
 * agent启用消息处理器
 *
 * @author zhang
 * @date 2024-10-30 10:34
 */
public class AgentEnableMessageHandler implements ServerMessageHandler<AgentEnableMessage> {

    @Override
    public void handle(AgentEnableMessage data, AgentSocketClient session) {
        TraceMasterContext.setEnable(data.getEnable());
    }

}
