package com.zhang.trace.master.agent.socket.handler.impl;

import com.zhang.trace.master.agent.socket.AgentSocketClient;
import com.zhang.trace.master.agent.socket.handler.ServerMessageHandler;
import com.zhang.trace.master.core.socket.request.domain.RegistryResultMessage;

/**
 * 注册结果消息处理器
 *
 * @author zhang
 * @date 2024-10-18 16:56
 */
public class RegistryResultMessageHandler implements ServerMessageHandler<RegistryResultMessage> {

    @Override
    public void handle(RegistryResultMessage registryResultRequest, AgentSocketClient session) {
        String instanceId = registryResultRequest.getInstanceId();
        if (null == instanceId || instanceId.isBlank()) {
            throw new RuntimeException("注册失败，请检查 server 端状态");
        }
        session.setInstanceId(instanceId);

        // 拉取配置
        session.fetchConfig();

        // 向 server 发送心跳
        session.heartbeat();
    }

}
