package com.zhang.trace.master.server.socket.handler.impl;

import com.zhang.trace.master.core.config.socket.request.ServerRequest;
import com.zhang.trace.master.core.config.socket.request.ServerRequestType;
import com.zhang.trace.master.core.config.socket.request.domain.HeartBeatRequest;
import com.zhang.trace.master.core.config.socket.request.domain.HeartBeatResultRequest;
import com.zhang.trace.master.server.socket.WebSocketSessionManager;
import com.zhang.trace.master.server.socket.handler.AgentRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

/**
 * 心跳消息处理
 *
 * @author zhang
 * @date 2024-10-16 17:22
 */
@Slf4j
public class HeartBeatRequestHandler implements AgentRequestHandler<HeartBeatRequest> {

    private static final String PING = "ping";

    private static final String PONG = "pong";

    @Override
    public void handle(HeartBeatRequest heartBeatRequest, WebSocketSession session) {
        log.info("收到心跳请求:" + heartBeatRequest);
        if (!Objects.equals(PING, heartBeatRequest.getPing())) {
            throw new RuntimeException("wrong heartbeat data:" + heartBeatRequest.getPing());
        }
        HeartBeatResultRequest heartBeatResponse = new HeartBeatResultRequest();
        heartBeatResponse.setPong(PONG);

        ServerRequest<HeartBeatResultRequest> serverRequest = new ServerRequest<>();
        serverRequest.setData(heartBeatResponse);
        serverRequest.setType(ServerRequestType.HEARTBEAT_RESULT);

        WebSocketSessionManager.sendMessage(heartBeatRequest.getAppId(), heartBeatRequest.getInstanceId(), serverRequest);
    }

}
