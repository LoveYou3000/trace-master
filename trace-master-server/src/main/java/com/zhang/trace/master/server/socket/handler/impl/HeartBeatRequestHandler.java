package com.zhang.trace.master.server.socket.handler.impl;

import com.zhang.trace.master.server.socket.WebSocketSessionManager;
import com.zhang.trace.master.server.socket.handler.AgentRequestHandler;
import com.zhang.trace.master.core.config.socket.request.domain.HeartBeatRequest;
import com.zhang.trace.master.core.config.socket.response.domain.HeartBeatResponse;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

/**
 * 心跳消息处理
 *
 * @author zhang
 * @date 2024-10-16 17:22
 */
public class HeartBeatRequestHandler implements AgentRequestHandler<HeartBeatRequest> {

    private static final String PING = "ping";

    private static final String PONG = "pong";

    @Override
    public void handle(HeartBeatRequest heartBeatRequest, WebSocketSession session) {
        if (!Objects.equals(PING, heartBeatRequest.getPing())) {
            throw new RuntimeException("wrong heartbeat data:" + heartBeatRequest.getPing());
        }
        HeartBeatResponse heartBeatResponse = new HeartBeatResponse();
        heartBeatResponse.setPong(PONG);
        WebSocketSessionManager.sendMessage(heartBeatRequest.getAppId(), heartBeatRequest.getInstanceId(), heartBeatResponse);
    }

}
