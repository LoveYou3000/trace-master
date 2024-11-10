package com.zhang.trace.master.agent.socket.handler.impl;

import com.zhang.trace.master.agent.interceptor.context.TraceMasterContext;
import com.zhang.trace.master.agent.socket.AgentSocketClient;
import com.zhang.trace.master.agent.socket.handler.ServerMessageHandler;
import com.zhang.trace.master.core.socket.request.domain.AgentEnableMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * agent启用消息处理器
 *
 * @author zhang
 * @date 2024-10-30 10:34
 */
@Slf4j
public class AgentEnableMessageHandler implements ServerMessageHandler<AgentEnableMessage> {

    @Override
    public void handle(AgentEnableMessage agentEnableMessage, AgentSocketClient session) {
        log.info("设置全局开关为:{}", agentEnableMessage.getEnable());
        TraceMasterContext.setGlobalEnable(agentEnableMessage.getEnable());
    }

}
