package com.zhang.trace.master.agent.socket.handler.impl;

import com.zhang.trace.master.agent.socket.handler.ServerRequestHandler;
import com.zhang.trace.master.core.config.socket.request.domain.HeartBeatResultRequest;
import org.java_websocket.client.WebSocketClient;

import java.util.Objects;

/**
 * 收到心跳结果的消息
 *
 * @author zhang
 * @date 2024-10-18 17:07
 */
public class HeartBeatResultRequestHandler implements ServerRequestHandler<HeartBeatResultRequest> {

    private static final String PONG = "pong";

    @Override
    public void handle(HeartBeatResultRequest heartBeatResultRequest, WebSocketClient session) {
        if (!Objects.equals(heartBeatResultRequest.getPong(), PONG)) {
            throw new RuntimeException("wrong heartbeat result data:" + heartBeatResultRequest.getPong());
        }
    }

}
