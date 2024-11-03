package com.zhang.trace.master.agent.interceptor.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.zhang.trace.master.agent.socket.AgentSocketClient;
import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
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
    private static Boolean isGlobalEnable;

    public static boolean isGlobalEnable() {
        return Boolean.TRUE.equals(isGlobalEnable);
    }

    public static void setIsGlobalEnable(boolean isGlobalEnable) {
        TraceMasterContext.isGlobalEnable = isGlobalEnable;
    }

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

}
