package com.zhang.trace.master.server.socket.message.handler;

import com.zhang.trace.master.server.socket.domain.AgentMessage;
import com.zhang.trace.master.server.socket.WebSocketSessionManager;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

/**
 * 心跳消息处理
 *
 * @author zhang
 * @date 2024-10-16 17:22
 */
public class HeartBeatMessageHandler implements AgentMessageHandler {

    private static final String PING = "ping";

    private static final String PONG = "pong";

    @Override
    public void handleAgentMessage(AgentMessage agentMessage, WebSocketSession session) {
        if (Objects.equals(PING, agentMessage.getMsg())) {
            WebSocketSessionManager.sendMessage(session, PONG);
        }
    }

}
