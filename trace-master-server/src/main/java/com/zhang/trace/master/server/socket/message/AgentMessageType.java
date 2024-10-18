package com.zhang.trace.master.server.socket.message;

import com.zhang.trace.master.server.socket.message.domain.FetchConfigMessage;
import com.zhang.trace.master.server.socket.message.domain.HeartBeatMessage;
import com.zhang.trace.master.server.socket.message.domain.RegistryMessage;
import com.zhang.trace.master.server.socket.message.domain.UnRegistryMessage;
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
    HEARTBEAT(new HeartBeatMessageHandler(), HeartBeatMessage.class),

    /**
     * 注册
     */
    REGISTER(new RegisterMessageHandler(), RegistryMessage.class),

    /**
     * 反注册
     */
    UNREGISTER(new UnRegisterMessageHandler(), UnRegistryMessage.class),

    /**
     * 拉取配置信息
     */
    FETCH_CONFIG(new FetchConfigMessageHandler(), FetchConfigMessage.class),
    ;

    private final AgentMessageHandler<?> handler;

    private final Class<?> paramKlz;

}
