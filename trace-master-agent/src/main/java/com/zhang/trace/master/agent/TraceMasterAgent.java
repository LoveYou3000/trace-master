package com.zhang.trace.master.agent;

import com.zhang.trace.master.agent.interceptor.TraceInterceptor;
import com.zhang.trace.master.agent.socket.AgentSocketClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * trace-master agent入口类
 *
 * @author zhang
 * @date 2024-09-30 17:11
 */
@Slf4j
public class TraceMasterAgent {

    private static final ExecutorService SOCKET_EXECUTOR = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1),
            r -> {
                Thread th = new Thread(r);
                th.setName("socket-agent");
                return th;
            }, new ThreadPoolExecutor.AbortPolicy());

    public static void premain(String agentArgs, Instrumentation inst) {
        if (null == agentArgs || agentArgs.isBlank()) {
            log.error("未配置 trace master 服务端地址");
            return;
        }

        // 获取主类的名称，当作 appId
        String mainClass = System.getProperty("sun.java.command").split(" ")[0];
        String appId = mainClass.substring(mainClass.lastIndexOf(".") + 1);

        // 异步创建与 server 端的连接
        AgentSocketClient socketClient = createSocketConn(agentArgs, appId);

        // 设置需要进行增强的类
        ElementMatcher.Junction<? super TypeDescription> classMatcher = classMatcher();

        // 设置了 main 方法，get/set 方法不进行增强
        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule, protectionDomain) -> builder.method(methodMatcher(typeDescription)).intercept(MethodDelegation.to(TraceInterceptor.class));

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

    private static ElementMatcher.Junction<? super MethodDescription> methodMatcher(TypeDescription typeDescription) {
        return ElementMatchers.not(
                        ElementMatchers.isMain()
                                .or(ElementMatchers.isGetter())
                                .or(ElementMatchers.isSetter())
                                .or(ElementMatchers.isClone())
                )
                .and(ElementMatchers.isDeclaredBy(typeDescription));
    }

    private static ElementMatcher.Junction<? super TypeDescription> classMatcher() {
        return ElementMatchers.not(
                ElementMatchers.isAnnotation()
        );
    }

}
