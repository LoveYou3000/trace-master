package com.zhang.trace.master.agent.socket;

import com.zhang.trace.master.agent.socket.handler.ServerMessageHandler;
import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
import com.zhang.trace.master.core.config.socket.request.SocketMessage;
import com.zhang.trace.master.core.config.socket.request.SocketMessageType;
import com.zhang.trace.master.core.config.socket.request.domain.HeartBeatMessage;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryMessage;
import com.zhang.trace.master.core.config.socket.request.domain.UnRegistryMessage;
import com.zhang.trace.master.core.config.util.JacksonUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * agent 向 server 发起会话
 *
 * @author zhang
 * @date 2024-10-18 15:16
 */
@Slf4j
public class AgentSocketClient extends WebSocketClient {

    private static final String PING = "ping";

    private final ScheduledExecutorService HEARTBEAT_EXECUTOR = Executors.newSingleThreadScheduledExecutor();

    private final String appId;

    @Setter
    private String instanceId;

    public AgentSocketClient(URI serverUri, String appId) {
        super(serverUri);
        this.appId = appId;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        register();
        heartbeat();
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void onMessage(String s) {
        SocketMessage<?> serverMessage = JacksonUtil.parseObj(s, SocketMessage.class);
        ServerMessageHandler.getServerRequestHandler(serverMessage.type()).handleMessage(serverMessage.data(), this);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        unRegister();
    }

    @Override
    public void onError(Exception e) {

    }

    private void send(SocketMessage<?> agentMessage) {
        send(JacksonUtil.toJsonString(agentMessage));
    }

    private void register() {
        RegistryMessage registryRequest = new RegistryMessage();
        registryRequest.setAppId(appId);

        SocketMessage<RegistryMessage> agentMessage = new SocketMessage<>(registryRequest, SocketMessageType.REGISTER);

        send(agentMessage);
    }

    private void unRegister() {
        UnRegistryMessage unRegistryRequest = new UnRegistryMessage();
        unRegistryRequest.setAppId(appId);
        unRegistryRequest.setInstanceId(instanceId);

        SocketMessage<UnRegistryMessage> agentMessage = new SocketMessage<>(unRegistryRequest, SocketMessageType.UNREGISTER);

        send(agentMessage);
    }

    private void heartbeat() {
        HEARTBEAT_EXECUTOR.scheduleAtFixedRate(() -> {
            HeartBeatMessage heartBeatRequest = new HeartBeatMessage();
            heartBeatRequest.setPing(PING);
            heartBeatRequest.setAppId(appId);
            heartBeatRequest.setInstanceId(instanceId);

            SocketMessage<HeartBeatMessage> agentMessage = new SocketMessage<>(heartBeatRequest, SocketMessageType.HEARTBEAT);

            send(agentMessage);
        }, 10, 10, TimeUnit.SECONDS);
    }

    public TraceMasterAgentConfig fetchConfig() {
        TraceMasterAgentConfig config = new TraceMasterAgentConfig();
        config.setIncludePackages(Collections.singleton("com.zhang.test.trace"));
        return config;
    }

}
