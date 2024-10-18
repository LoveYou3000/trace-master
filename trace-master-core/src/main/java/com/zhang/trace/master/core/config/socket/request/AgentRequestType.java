package com.zhang.trace.master.core.config.socket.request;

import com.zhang.trace.master.core.config.socket.request.domain.BaseRequest;
import com.zhang.trace.master.core.config.socket.request.domain.FetchConfigRequest;
import com.zhang.trace.master.core.config.socket.request.domain.HeartBeatRequest;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryRequest;
import com.zhang.trace.master.core.config.socket.request.domain.UnRegistryRequest;
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
public enum AgentRequestType {

    /**
     * 心跳
     */
    HEARTBEAT(HeartBeatRequest.class),

    /**
     * 注册
     */
    REGISTER(RegistryRequest.class),

    /**
     * 反注册
     */
    UNREGISTER(UnRegistryRequest.class),

    /**
     * 拉取配置信息
     */
    FETCH_CONFIG(FetchConfigRequest.class),
    ;

    /**
     * 消息内实体类的类型
     */
    private final Class<? extends BaseRequest> requestKlz;

}
