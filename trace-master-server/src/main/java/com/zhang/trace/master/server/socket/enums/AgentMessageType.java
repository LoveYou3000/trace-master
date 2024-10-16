package com.zhang.trace.master.server.socket.enums;

import com.zhang.trace.master.server.socket.message.handler.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * agent 向 server 发送的消息类型
 *
 * @author zhang
 * @date 2024-10-16 17:06
 */
@RequiredArgsConstructor
@Getter
public enum AgentMessageType {

    /**
     * 心跳
     */
    HEARTBEAT(new HeartBeatMessageHandler()),

    /**
     * 注册
     */
    REGISTER(new RegisterMessageHandler()),

    /**
     * 反注册
     */
    UNREGISTER(new UnRegisterMessageHandler()),

    /**
     * 拉取配置信息
     */
    FETCH_CONFIG(new FetchConfigMessageHandler()),
    ;

    private final AgentMessageHandler handler;

}
