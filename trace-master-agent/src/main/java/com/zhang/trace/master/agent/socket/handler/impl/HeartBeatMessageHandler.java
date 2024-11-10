package com.zhang.trace.master.agent.socket.handler.impl;

import com.zhang.trace.master.agent.socket.AgentSocketClient;
import com.zhang.trace.master.agent.socket.handler.ServerMessageHandler;
import com.zhang.trace.master.core.socket.request.domain.HeartBeatMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 心跳结果消息处理器
 *
 * @author zhang
 * @date 2024-10-18 17:07
 */
@Slf4j
public class HeartBeatMessageHandler implements ServerMessageHandler<HeartBeatMessage> {

    private static final String PONG = "pong";

    @Override
    public void handle(HeartBeatMessage heartBeatMessage, AgentSocketClient session) {
        log.debug("收到心跳消息:{}", heartBeatMessage);
        if (!Objects.equals(heartBeatMessage.getPong(), PONG)) {
            throw new RuntimeException("wrong heartbeat data:" + heartBeatMessage.getPong());
        }
        // 向 server 发送心跳
        session.heartbeat();
    }

}
