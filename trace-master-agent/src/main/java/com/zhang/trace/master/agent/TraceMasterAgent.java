package com.zhang.trace.master.agent;

import com.zhang.trace.master.agent.socket.AgentSocketClient;

import java.lang.instrument.Instrumentation;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * trace-master agent入口类
 *
 * @author zhang
 * @date 2024-09-30 17:11
 */
public class TraceMasterAgent {

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        AgentSocketClient client = new AgentSocketClient(new URI("ws://localhost:8080/socket"));
        client.connectBlocking();
    }

    public static void premain(String agentArgs, Instrumentation inst) {

    }

}
