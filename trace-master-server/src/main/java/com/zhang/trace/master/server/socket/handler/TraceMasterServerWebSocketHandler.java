package com.zhang.trace.master.server.socket.handler;

import com.zhang.trace.master.core.config.socket.request.AgentMessage;
import com.zhang.trace.master.core.config.util.JacksonUtil;
import jakarta.annotation.Nonnull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * websocket 消息处理器
 *
 * @author zhang
 * @date 2024-10-16 17:00
 */
public class TraceMasterServerWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(@Nonnull WebSocketSession session, TextMessage message) throws Exception {
        AgentMessage<?> agentMessage = JacksonUtil.parseObj(message.getPayload(), AgentMessage.class);
        AgentRequestHandler.getAgentRequestHandler(agentMessage.getType()).handleMessage(agentMessage.getData(), session);
    }
}
