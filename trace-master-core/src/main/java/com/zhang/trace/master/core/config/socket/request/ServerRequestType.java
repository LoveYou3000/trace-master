package com.zhang.trace.master.core.config.socket.request;

import com.zhang.trace.master.core.config.socket.request.domain.BaseRequest;
import com.zhang.trace.master.core.config.socket.request.domain.ConfigUpdatedRequest;
import com.zhang.trace.master.core.config.socket.request.domain.HeartBeatResultRequest;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryResultRequest;
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
public enum ServerRequestType {

    /**
     * 心跳结果
     */
    HEARTBEAT_RESULT(HeartBeatResultRequest.class),

    /**
     * 注册结果
     */
    REGISTRY_RESULT(RegistryResultRequest.class),

    /**
     * 配置已更新
     */
    CONFIG_UPDATED(ConfigUpdatedRequest.class),

    ;

    private final Class<? extends BaseRequest> requestKlz;

}
