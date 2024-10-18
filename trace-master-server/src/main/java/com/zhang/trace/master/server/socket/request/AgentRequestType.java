package com.zhang.trace.master.server.socket.request;

import com.zhang.trace.master.core.config.socket.request.domain.BaseRequest;
import com.zhang.trace.master.server.socket.handler.AgentRequestHandler;
import com.zhang.trace.master.server.socket.handler.impl.FetchConfigRequestHandler;
import com.zhang.trace.master.server.socket.handler.impl.HeartBeatRequestHandler;
import com.zhang.trace.master.server.socket.handler.impl.RegisterRequestHandler;
import com.zhang.trace.master.server.socket.handler.impl.UnRegisterRequestHandler;
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
    HEARTBEAT(new HeartBeatRequestHandler(), HeartBeatRequest.class),

    /**
     * 注册
     */
    REGISTER(new RegisterRequestHandler(), RegistryRequest.class),

    /**
     * 反注册
     */
    UNREGISTER(new UnRegisterRequestHandler(), UnRegistryRequest.class),

    /**
     * 拉取配置信息
     */
    FETCH_CONFIG(new FetchConfigRequestHandler(), FetchConfigRequest.class),
    ;

    /**
     * 对应的消息处理器
     */
    private final AgentRequestHandler<?> handler;

    /**
     * 消息内实体类的类型
     */
    private final Class<? extends BaseRequest> requestKlz;

}
