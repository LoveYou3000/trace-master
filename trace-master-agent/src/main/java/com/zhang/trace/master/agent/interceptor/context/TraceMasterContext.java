package com.zhang.trace.master.agent.interceptor.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.zhang.trace.master.agent.socket.AgentSocketClient;
import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
import io.opentracing.mock.MockSpan;
import io.opentracing.mock.MockTracer;
import lombok.Getter;
import lombok.Setter;

/**
 * 上下文
 *
 * @author zhang
 * @date 2024-10-29 09:02
 */
public class TraceMasterContext {

    /**
     * socket client
     */
    @Setter
    @Getter
    private static AgentSocketClient agentSocketClient;

    /**
     * 配置
     */
    @Setter
    @Getter
    private static TraceMasterAgentConfig traceMasterAgentConfig;

    /**
     * 全局启用标识
     */
    @Setter
    @Getter
    private static boolean isGlobalEnable = true;

    /**
     * 方法级入口标识
     */
    private static final ThreadLocal<Boolean> ENABLE = new TransmittableThreadLocal<>() {
        @Override
        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    };

    public static boolean isEnable() {
        return Boolean.TRUE.equals(ENABLE.get());
    }

    public static void setEnable(boolean enable) {
        ENABLE.set(enable);
    }

    /**
     * span
     */
    private static final ThreadLocal<MockSpan> SPAN = new TransmittableThreadLocal<>();

    public static MockSpan getSpan() {
        return SPAN.get();
    }

    public static void setSpan(MockSpan span) {
        SPAN.set(span);
    }

    /**
     * tracer
     */
    private static final ThreadLocal<MockTracer> TRACER = new TransmittableThreadLocal<>() {
        @Override
        protected MockTracer initialValue() {
            return new MockTracer();
        }
    };

    public static MockTracer getTracer() {
        return TRACER.get();
    }

    public static void clear() {
        ENABLE.remove();
        SPAN.remove();
        TRACER.remove();
    }

}
