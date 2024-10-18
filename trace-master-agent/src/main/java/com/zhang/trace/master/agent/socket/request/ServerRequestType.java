package com.zhang.trace.master.agent.socket.request;

import com.zhang.trace.master.agent.socket.handler.ServerRequestHandler;
import com.zhang.trace.master.agent.socket.handler.impl.ConfigUpdatedRequestHandler;
import com.zhang.trace.master.core.config.socket.request.domain.ConfigUpdatedRequest;
import com.zhang.trace.master.core.config.socket.request.domain.BaseRequest;
import lombok.RequiredArgsConstructor;

/**
 * server 向 agent 发送的消息类型
 *
 * @author zhang
 * @date 2024-10-16 17:18
 */
@RequiredArgsConstructor
public enum ServerRequestType {

    /**
     * 配置已更新
     */
    CONFIG_UPDATED(new ConfigUpdatedRequestHandler(), ConfigUpdatedRequest.class);

    private final ServerRequestHandler<?> handler;

    private final Class<? extends BaseRequest> paramKlz;

}
