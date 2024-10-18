package com.zhang.trace.master.agent.socket;

import com.zhang.trace.master.agent.socket.handler.ServerRequestHandler;
import com.zhang.trace.master.core.config.socket.request.AgentRequest;
import com.zhang.trace.master.core.config.socket.request.AgentRequestType;
import com.zhang.trace.master.core.config.socket.request.ServerRequest;
import com.zhang.trace.master.core.config.socket.request.domain.HeartBeatRequest;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryRequest;
import com.zhang.trace.master.core.config.socket.request.domain.UnRegistryRequest;
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
        ServerRequest<?> serverRequest = JacksonUtil.parseObj(s, ServerRequest.class);
        ServerRequestHandler.getServerRequestHandler(serverRequest.getType()).handleMessage(serverRequest.getData(), this);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        unRegister();
    }

    @Override
    public void onError(Exception e) {

    }

    private void send(AgentRequest<?> agentRequest) {
        send(JacksonUtil.toJsonString(agentRequest));
    }

    private void register() {
        RegistryRequest registryRequest = new RegistryRequest();
        registryRequest.setAppId(appId);

        AgentRequest<RegistryRequest> agentRequest = new AgentRequest<>();
        agentRequest.setData(registryRequest);
        agentRequest.setType(AgentRequestType.REGISTER);

        send(agentRequest);
    }

    private void unRegister() {
        UnRegistryRequest unRegistryRequest = new UnRegistryRequest();
        unRegistryRequest.setAppId(appId);
        unRegistryRequest.setInstanceId(instanceId);

        AgentRequest<UnRegistryRequest> agentRequest = new AgentRequest<>();
        agentRequest.setData(unRegistryRequest);
        agentRequest.setType(AgentRequestType.UNREGISTER);

        send(agentRequest);
    }

    private void heartbeat() {
        HEARTBEAT_EXECUTOR.scheduleAtFixedRate(() -> {
            HeartBeatRequest heartBeatRequest = new HeartBeatRequest();
            heartBeatRequest.setPing(PING);
            heartBeatRequest.setAppId(appId);
            heartBeatRequest.setInstanceId(instanceId);

            AgentRequest<HeartBeatRequest> agentRequest = new AgentRequest<>();
            agentRequest.setData(heartBeatRequest);
            agentRequest.setType(AgentRequestType.HEARTBEAT);

            send(agentRequest);
        }, 10, 10, TimeUnit.SECONDS);


    }

}
