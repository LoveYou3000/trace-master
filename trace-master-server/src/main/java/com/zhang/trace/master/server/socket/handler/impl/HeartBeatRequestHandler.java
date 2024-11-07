package com.zhang.trace.master.server.socket.handler.impl;

import com.zhang.trace.master.core.config.socket.request.SocketMessage;
import com.zhang.trace.master.core.config.socket.request.SocketMessageType;
import com.zhang.trace.master.core.config.socket.request.domain.HeartBeatMessage;
import com.zhang.trace.master.server.socket.WebSocketSessionManager;
import com.zhang.trace.master.server.socket.handler.AgentRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

/**
 * 心跳消息处理器
 *
 * @author zhang
 * @date 2024-10-16 17:22
 */
@Slf4j
public class HeartBeatRequestHandler implements AgentRequestHandler<HeartBeatMessage> {

    private static final String PING = "ping";

    private static final String PONG = "pong";

    @Override
    public void handle(HeartBeatMessage heartBeatRequest, WebSocketSession session) {
        log.debug("收到心跳消息:{}", heartBeatRequest);
        if (!Objects.equals(PING, heartBeatRequest.getPing())) {
            throw new RuntimeException("wrong heartbeat data:" + heartBeatRequest.getPing());
        }
        HeartBeatMessage heartBeatResponse = new HeartBeatMessage();
        heartBeatResponse.setPong(PONG);

        SocketMessage<HeartBeatMessage> serverMessage = new SocketMessage<>(heartBeatResponse, SocketMessageType.HEARTBEAT_RESULT);

        WebSocketSessionManager.sendMessage(session, serverMessage);
    }

}
