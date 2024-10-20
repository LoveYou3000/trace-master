package com.zhang.trace.master.core.config.socket.request;

import com.zhang.trace.master.core.config.socket.request.domain.BaseSocketMessage;
import com.zhang.trace.master.core.config.socket.request.domain.ConfigUpdatedMessage;
import com.zhang.trace.master.core.config.socket.request.domain.HeartBeatMessage;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryResultMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * server 向 agent 发送的消息类型
 *
 * @author zhang
 * @date 2024-10-16 17:18
 */
@RequiredArgsConstructor
@Getter
public enum ServerMessageType {

    /**
     * 心跳结果
     */
    HEARTBEAT_RESULT(HeartBeatMessage.class),

    /**
     * 注册结果
     */
    REGISTRY_RESULT(RegistryResultMessage.class),

    /**
     * 配置已更新
     */
    CONFIG_UPDATED(ConfigUpdatedMessage.class),

    ;

    private final Class<? extends BaseSocketMessage> requestKlz;

}
