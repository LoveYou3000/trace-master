package com.zhang.trace.master.server.socket.handler.impl;

import com.zhang.trace.master.server.socket.handler.AgentRequestHandler;
import com.zhang.trace.master.core.config.socket.request.domain.FetchConfigMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * 拉取配置消息处理器
 *
 * @author zhang
 * @date 2024-10-16 17:35
 */
public class FetchConfigRequestHandler implements AgentRequestHandler<FetchConfigMessage> {

    @Override
    public void handle(FetchConfigMessage data, WebSocketSession session) {
        // TODO 拉取配置消息处理
        // WebSocketSessionManager.sendMessage(session, 最新的配置);
    }

}
