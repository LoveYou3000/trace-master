package com.zhang.trace.master.server.socket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zhang.trace.master.core.config.socket.request.AgentRequest;
import com.zhang.trace.master.core.config.socket.request.AgentRequestDeserializer;
import com.zhang.trace.master.core.config.util.JacksonUtil;
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
        AgentRequest<?> agentRequest = JacksonUtil.parseObj(message.getPayload(), AgentRequest.class);
        AgentRequestHandler.getAgentRequestHandler(agentRequest.getType()).handleMessage(agentRequest.getData(), session);
    }
}
