package com.zhang.trace.master.agent;

import com.zhang.trace.master.agent.interceptor.TraceInterceptor;
import com.zhang.trace.master.agent.socket.AgentSocketClient;
import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * trace-master agent入口类
 *
 * @author zhang
 * @date 2024-09-30 17:11
 */
@Slf4j
public class TraceMasterAgent {

    private static final ExecutorService SOCKET_EXECUTOR = Executors.newSingleThreadExecutor();

    public static void premain(String agentArgs, Instrumentation inst) {
        if (null == agentArgs || agentArgs.isBlank()) {
            log.error("未配置 trace master 服务端地址");
            return;
        }

        // 获取主类的名称，当作 appId
        String mainClass = System.getProperty("sun.java.command").split(" ")[0];
        String appId = mainClass.substring(mainClass.lastIndexOf(".") + 1);
        System.out.println(appId);

        // 异步创建与 server 端的连接
        AgentSocketClient socketClient = createSocketConn(agentArgs, appId);
        TraceMasterAgentConfig config = socketClient.fetchConfig();

        // 设置需要进行增强的类
        ElementMatcher.Junction<NamedElement> classMatcher = classMatcher(config);

        // 设置了 main 方法，get/set 方法不进行增强
        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule, protectionDomain) -> builder.method(methodMatcher()).intercept(MethodDelegation.to(TraceInterceptor.class));

        // 监听器无操作
        AgentBuilder.Listener listener = AgentBuilder.Listener.NoOp.INSTANCE;

        new AgentBuilder.Default()
                .type(classMatcher)
                .transform(transformer)
                .with(listener)
                .installOn(inst);
    }

    @SneakyThrows(URISyntaxException.class)
    private static AgentSocketClient createSocketConn(String socketServer, String appId) {
        AgentSocketClient client = new AgentSocketClient(new URI("ws://" + socketServer + "/socket"), appId);
        SOCKET_EXECUTOR.execute(() -> {
            try {
                client.connectBlocking();
            } catch (InterruptedException e) {
                log.error("socket 连接失败");
                throw new RuntimeException(e);
            }
        });
        return client;
    }

    private static ElementMatcher.Junction<MethodDescription> methodMatcher() {
        return ElementMatchers.not(
                ElementMatchers.isMain()
                        .or(ElementMatchers.isGetter())
                        .or(ElementMatchers.isSetter())
        );
    }

    private static ElementMatcher.Junction<NamedElement> classMatcher(TraceMasterAgentConfig config) {
        ElementMatcher.Junction<NamedElement> matcher = null;
        if (Objects.isNull(config) || Objects.isNull(config.getIncludePackages()) || config.getIncludePackages().isEmpty()) {
            throw new RuntimeException("未配置需要监控的包名");
        }

        for (String includePackage : config.getIncludePackages()) {
            ElementMatcher.Junction<NamedElement> curMatcher = ElementMatchers.nameStartsWith(includePackage);
            if (Objects.isNull(matcher)) {
                matcher = curMatcher;
            } else {
                matcher = matcher.or(curMatcher);
            }
        }

        return matcher;
    }

}
