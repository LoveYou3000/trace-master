package com.zhang.trace.master.server.socket.handler;

import com.zhang.trace.master.core.config.socket.request.SocketMessage;
import com.zhang.trace.master.core.config.util.JacksonUtil;
import org.springframework.lang.NonNull;
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
    protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) throws Exception {
        SocketMessage<?> agentMessage = JacksonUtil.parseObj(message.getPayload(), SocketMessage.class);
        AgentRequestHandler.getAgentRequestHandler(agentMessage.type()).handleMessage(agentMessage.data(), session);
    }

}
