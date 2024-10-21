package com.zhang.trace.master.agent.socket;

import com.zhang.trace.master.agent.socket.handler.ServerMessageHandler;
import com.zhang.trace.master.core.config.socket.request.AgentMessage;
import com.zhang.trace.master.core.config.socket.request.AgentMessageType;
import com.zhang.trace.master.core.config.socket.request.ServerMessage;
import com.zhang.trace.master.core.config.socket.request.domain.HeartBeatMessage;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryMessage;
import com.zhang.trace.master.core.config.socket.request.domain.UnRegistryMessage;
import com.zhang.trace.master.core.config.util.JacksonUtil;
import lombok.Setter;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * agent 向 server 发起会话
 *
 * @author zhang
 * @date 2024-10-18 15:16
 */
public class AgentSocketClient extends WebSocketClient {

    private static final String PING = "ping";

    private static final ScheduledExecutorService HEARTBEAT_EXECUTOR = Executors.newSingleThreadScheduledExecutor();

    private static final String appId = "java-normal-test";

    @Setter
    private static String instanceId;

    public AgentSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        register();
        heartbeat();
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void onMessage(String s) {
        ServerMessage<?> serverMessage = JacksonUtil.parseObj(s, ServerMessage.class);
        ServerMessageHandler.getServerRequestHandler(serverMessage.getType()).handleMessage(serverMessage.getData(), this);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        unRegister();
    }

    @Override
    public void onError(Exception e) {

    }

    private void send(AgentMessage<?> agentMessage) {
        send(JacksonUtil.toJsonString(agentMessage));
    }

    private void register() {
        RegistryMessage registryRequest = new RegistryMessage();
        registryRequest.setAppId(appId);

        AgentMessage<RegistryMessage> agentMessage = new AgentMessage<>();
        agentMessage.setData(registryRequest);
        agentMessage.setType(AgentMessageType.REGISTER);

        send(agentMessage);
    }

    private void unRegister() {
        UnRegistryMessage unRegistryRequest = new UnRegistryMessage();
        unRegistryRequest.setAppId(appId);
        unRegistryRequest.setInstanceId(instanceId);

        AgentMessage<UnRegistryMessage> agentMessage = new AgentMessage<>();
        agentMessage.setData(unRegistryRequest);
        agentMessage.setType(AgentMessageType.UNREGISTER);

        send(agentMessage);
    }

    private void heartbeat() {
        HEARTBEAT_EXECUTOR.scheduleAtFixedRate(() -> {
            HeartBeatMessage heartBeatRequest = new HeartBeatMessage();
            heartBeatRequest.setPing(PING);
            heartBeatRequest.setAppId(appId);
            heartBeatRequest.setInstanceId(instanceId);

            AgentMessage<HeartBeatMessage> agentMessage = new AgentMessage<>();
            agentMessage.setData(heartBeatRequest);
            agentMessage.setType(AgentMessageType.HEARTBEAT);

            send(agentMessage);
        }, 10, 10, TimeUnit.SECONDS);
    }

}