package com.zhang.trace.master.core.config.socket.request;

import com.zhang.trace.master.core.config.socket.request.domain.BaseSocketMessage;
import com.zhang.trace.master.core.config.socket.request.domain.FetchConfigMessage;
import com.zhang.trace.master.core.config.socket.request.domain.HeartBeatMessage;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryMessage;
import com.zhang.trace.master.core.config.socket.request.domain.UnRegistryMessage;
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
    HEARTBEAT(HeartBeatMessage.class),

    /**
     * 注册
     */
    REGISTER(RegistryMessage.class),

    /**
     * 反注册
     */
    UNREGISTER(UnRegistryMessage.class),

    /**
     * 拉取配置信息
     */
    FETCH_CONFIG(FetchConfigMessage.class),
    ;

    /**
     * 消息内实体类的类型
     */
    private final Class<? extends BaseSocketMessage> requestKlz;

}
