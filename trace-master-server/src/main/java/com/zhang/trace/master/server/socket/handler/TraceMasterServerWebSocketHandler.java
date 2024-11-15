package com.zhang.trace.master.server.socket.handler;

import com.zhang.trace.master.core.socket.request.SocketMessage;
import com.zhang.trace.master.core.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TraceMasterServerWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) throws Exception {
        SocketMessage<?> agentMessage = JacksonUtil.parseObj(message.getPayload(), SocketMessage.class);
        log.info("收到消息，类型:{}，消息体:{}", agentMessage.type(), agentMessage.data());
        AgentRequestHandler.getAgentRequestHandler(agentMessage.type()).handleMessage(agentMessage.data(), session);
    }

}
