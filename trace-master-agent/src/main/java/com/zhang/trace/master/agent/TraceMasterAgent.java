package com.zhang.trace.master.agent;

import com.zhang.trace.master.agent.interceptor.TraceInterceptor;
import com.zhang.trace.master.agent.socket.AgentSocketClient;
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
        createSocketConn(agentArgs, appId);

        // 设置了本项目的路径不进行增强
        ElementMatcher.Junction<NamedElement> classMatcher = classMatcher();

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

    private static void createSocketConn(String socketServer, String appId) {
        SOCKET_EXECUTOR.execute(() -> {
            try {
                AgentSocketClient client = new AgentSocketClient(new URI("ws://" + socketServer + "/socket"), appId);
                client.connectBlocking();
            } catch (InterruptedException | URISyntaxException e) {
                log.error("socket 连接失败");
                throw new RuntimeException(e);
            }
        });
    }

    private static ElementMatcher.Junction<MethodDescription> methodMatcher() {
        return ElementMatchers.not(
                ElementMatchers.isMain()
                        .or(ElementMatchers.isGetter())
                        .or(ElementMatchers.isSetter())
                        .or(ElementMatchers.isClone())
                        .or(ElementMatchers.isConstructor())
        );
    }

    private static ElementMatcher.Junction<NamedElement> classMatcher() {
        return ElementMatchers.not(
                ElementMatchers.nameStartsWith("com.zhang.trace.master")
                        .or(ElementMatchers.nameContains("$"))
                        .or(ElementMatchers.nameStartsWith("org.springframework.util.LinkedMultiValueMap"))
                        .or(ElementMatchers.nameStartsWith("org.springframework.beans.factory.support.DefaultListableBeanFactory"))
                        .or(ElementMatchers.nameStartsWith("org.springframework.boot.SpringApplication"))
                        .or(ElementMatchers.nameStartsWith("org.springframework.web.context.support.GenericWebApplicationContext"))
        );
    }

}
