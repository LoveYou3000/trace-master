package com.zhang.trace.master.agent.socket;

import cn.hutool.core.thread.ThreadUtil;
import com.zhang.trace.master.agent.interceptor.context.TraceMasterContext;
import com.zhang.trace.master.agent.socket.handler.ServerMessageHandler;
import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
import com.zhang.trace.master.core.config.socket.request.SocketMessage;
import com.zhang.trace.master.core.config.socket.request.SocketMessageType;
import com.zhang.trace.master.core.config.socket.request.domain.BaseSocketMessage;
import com.zhang.trace.master.core.config.socket.request.domain.HeartBeatMessage;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryMessage;
import com.zhang.trace.master.core.config.socket.request.domain.UnRegistryMessage;
import com.zhang.trace.master.core.config.util.JacksonUtil;
import com.zhang.trace.master.core.config.util.NamedThreadFactory;
import io.opentracing.mock.MockSpan;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
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
        fetchConfig();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            unRegister();
            this.close();
        }));
    }

    @Override
    public void onMessage(String s) {
        SocketMessage<?> serverMessage = JacksonUtil.parseObj(s, SocketMessage.class);
        ServerMessageHandler.getServerRequestHandler(serverMessage.type()).handleMessage(serverMessage.data(), this);
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }

    private void send(BaseSocketMessage agentMessage, SocketMessageType socketMessageType) {
        SocketMessage<?> message = new SocketMessage<>(agentMessage, socketMessageType);

        send(JacksonUtil.toJsonString(message));
    }

    private void send(SocketMessage<?> agentMessage) {
        send(JacksonUtil.toJsonString(agentMessage));
    }

    private void register() {
        RegistryMessage registryRequest = new RegistryMessage();
        registryRequest.setAppId(appId);

        send(registryRequest, SocketMessageType.REGISTER);
    }

    private void unRegister() {
        UnRegistryMessage unRegistryRequest = new UnRegistryMessage();
        unRegistryRequest.setAppId(appId);
        unRegistryRequest.setInstanceId(instanceId);

        send(unRegistryRequest, SocketMessageType.UNREGISTER);
    }

    public void heartbeat() {
        ThreadUtil.sleep(10, TimeUnit.SECONDS);

        HeartBeatMessage heartBeatRequest = new HeartBeatMessage();
        heartBeatRequest.setAppId(appId);
        heartBeatRequest.setInstanceId(instanceId);
        heartBeatRequest.setPing(PING);

        send(heartBeatRequest, SocketMessageType.HEARTBEAT);
    }

    public void fetchConfig() {
        TraceMasterAgentConfig config = new TraceMasterAgentConfig();
        config.setIncludePackages(Collections.singleton("com.zhang.test.trace"));
        config.setMethodEntrances(Collections.singleton("com.zhang.test.trace.runner.PreheatRunner.run"));
        TraceMasterContext.setTraceMasterAgentConfig(config);
    }

    public void uploadFinishedSpans(List<MockSpan> finishedSpans) {
        log.info(finishedSpans.toString());
    }

}
