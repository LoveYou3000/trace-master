package com.zhang.trace.master.agent.socket.handler.impl;

import com.zhang.trace.master.agent.interceptor.context.TraceMasterContext;
import com.zhang.trace.master.agent.socket.AgentSocketClient;
import com.zhang.trace.master.agent.socket.handler.ServerMessageHandler;
import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
import com.zhang.trace.master.core.socket.request.domain.FetchConfigMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * 拉取到配置消息处理
 *
 * @author zhang
 * @date 2024-11-15 14:29
 */
@Slf4j
public class FetchConfigMessageHandler implements ServerMessageHandler<FetchConfigMessage> {

    @Override
    public void handle(FetchConfigMessage fetchConfigMessage, AgentSocketClient session) {
        TraceMasterAgentConfig config = fetchConfigMessage.getConfig();
        TraceMasterContext.setTraceMasterAgentConfig(config);
    }

}
