package com.zhang.trace.master.server.socket.handler.impl;

import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
import com.zhang.trace.master.core.socket.request.SocketMessage;
import com.zhang.trace.master.core.socket.request.SocketMessageType;
import com.zhang.trace.master.server.service.ConfigService;
import com.zhang.trace.master.server.socket.WebSocketSessionManager;
import com.zhang.trace.master.server.socket.handler.AgentRequestHandler;
import com.zhang.trace.master.core.socket.request.domain.FetchConfigMessage;
import com.zhang.trace.master.server.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

/**
 * 拉取配置消息处理器
 *
 * @author zhang
 * @date 2024-10-16 17:35
 */
@Slf4j
public class FetchConfigRequestHandler implements AgentRequestHandler<FetchConfigMessage> {

    private final ConfigService configService = SpringContextHolder.getSpringCtx().getBean(ConfigService.class);

    @Override
    public void handle(FetchConfigMessage fetchConfigMessage, WebSocketSession session) {
        String appId = fetchConfigMessage.getAppId();
        TraceMasterAgentConfig config = configService.getConfig(appId);

        FetchConfigMessage configMsg = new FetchConfigMessage();
        configMsg.setAppId(appId);
        configMsg.setInstanceId(fetchConfigMessage.getInstanceId());
        configMsg.setConfig(config);

        WebSocketSessionManager.sendMessage(session, new SocketMessage<>(configMsg, SocketMessageType.FETCH_CONFIG_RESULT));
    }

}
