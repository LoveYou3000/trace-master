package com.zhang.trace.master.server.socket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.trace.master.server.socket.domain.AgentMessage;
import jakarta.annotation.Nonnull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * agent 向 server 发送的消息处理器
 *
 * @author zhang
 * @date 2024-10-16 17:00
 */
public class TraceMasterServerWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(@Nonnull WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        AgentMessage agentMessage = new ObjectMapper().readValue(payload, AgentMessage.class);
        agentMessage.getType().getHandler().handleAgentMessage(agentMessage, session);
    }
}
