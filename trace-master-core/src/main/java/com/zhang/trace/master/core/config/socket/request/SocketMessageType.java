package com.zhang.trace.master.core.config.socket.request;

import com.zhang.trace.master.core.config.socket.request.domain.AgentEnableMessage;
import com.zhang.trace.master.core.config.socket.request.domain.BaseSocketMessage;
import com.zhang.trace.master.core.config.socket.request.domain.ConfigUpdatedMessage;
import com.zhang.trace.master.core.config.socket.request.domain.FetchConfigMessage;
import com.zhang.trace.master.core.config.socket.request.domain.HeartBeatMessage;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryMessage;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryResultMessage;
import com.zhang.trace.master.core.config.socket.request.domain.UnRegistryMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author zhang
 * @date 2024-10-21 17:05
 */
@RequiredArgsConstructor
@Getter
public enum SocketMessageType {

    /**
     * 心跳
     */
    HEARTBEAT(HeartBeatMessage.class),

    /**
     * 心跳结果
     */
    HEARTBEAT_RESULT(HeartBeatMessage.class),

    /**
     * 注册
     */
    REGISTER(RegistryMessage.class),

    /**
     * 注册结果
     */
    REGISTRY_RESULT(RegistryResultMessage.class),

    /**
     * 反注册
     */
    UNREGISTER(UnRegistryMessage.class),

    /**
     * 拉取配置信息
     */
    FETCH_CONFIG(FetchConfigMessage.class),

    /**
     * 配置已更新
     */
    CONFIG_UPDATED(ConfigUpdatedMessage.class),

    /**
     * agent启用
     */
    AGENT_ENABLE(AgentEnableMessage.class),

    ;

    /**
     * 消息内实体类的类型
     */
    private final Class<? extends BaseSocketMessage> requestKlz;

}
