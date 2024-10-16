package com.zhang.trace.master.server.socket.message.handler;

import com.zhang.trace.master.server.socket.domain.AgentMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * 注册消息处理
 *
 * @author zhang
 * @date 2024-10-16 17:24
 */
public class RegisterMessageHandler implements AgentMessageHandler {

    @Override
    public void handleAgentMessage(AgentMessage agentMessage, WebSocketSession session) {
        // TODO 注册消息处理
    }

}
