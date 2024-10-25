package com.zhang.trace.master.agent.socket.handler.impl;

import com.zhang.trace.master.agent.socket.AgentSocketClient;
import com.zhang.trace.master.agent.socket.handler.ServerMessageHandler;
import com.zhang.trace.master.core.config.socket.request.domain.HeartBeatMessage;

import java.util.Objects;

/**
 * 心跳结果消息处理器
 *
 * @author zhang
 * @date 2024-10-18 17:07
 */
public class HeartBeatMessageHandler implements ServerMessageHandler<HeartBeatMessage> {

    private static final String PONG = "pong";

    @Override
    public void handle(HeartBeatMessage heartBeatMessage, AgentSocketClient session) {
        if (!Objects.equals(heartBeatMessage.getPong(), PONG)) {
            throw new RuntimeException("wrong heartbeat data:" + heartBeatMessage.getPong());
        }
    }

}
