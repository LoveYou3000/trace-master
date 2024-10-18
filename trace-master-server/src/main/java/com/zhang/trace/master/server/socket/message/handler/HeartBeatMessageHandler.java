package com.zhang.trace.master.server.socket.message.handler;

import com.zhang.trace.master.server.socket.WebSocketSessionManager;
import com.zhang.trace.master.server.socket.message.domain.HeartBeatMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

/**
 * 心跳消息处理
 *
 * @author zhang
 * @date 2024-10-16 17:22
 */
public class HeartBeatMessageHandler implements AgentMessageHandler<HeartBeatMessage> {

    private static final String PING = "ping";

    private static final String PONG = "pong";

    @Override
    public void handle(HeartBeatMessage data, WebSocketSession session) {
        if (!Objects.equals(PING, data.getPing())) {
            throw new RuntimeException("wrong heartbeat data:" + data.getPing());
        }
        WebSocketSessionManager.sendMessage(session, PONG);
        // TODO 心跳保活
    }

}
