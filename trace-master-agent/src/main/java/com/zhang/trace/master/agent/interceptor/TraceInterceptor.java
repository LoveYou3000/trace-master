package com.zhang.trace.master.agent.interceptor;

import com.zhang.trace.master.agent.interceptor.context.TraceMasterContext;
import com.zhang.trace.master.core.config.util.MatchUtil;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * trace master 字节码增强
 *
 * @author zhang
 * @date 2024-10-24 09:23
 */
@Slf4j
public class TraceInterceptor {

    @RuntimeType
    public static <T> T interceptor(@Origin Method method, @SuperCall Callable<T> callable) throws Exception {
        // 全局未启用时，直接返回，不做任何处理
        if (!TraceMasterContext.isGlobalEnable()) {
            return callable.call();
        }

        // 类名及方法名不匹配，直接返回，不做任何处理
        Class<?> klz = method.getDeclaringClass();
        String klzName = klz.getName();
        String methodName = method.getName();
        if (!MatchUtil.classMatch(klzName) || !MatchUtil.methodMatch(methodName)) {
            return callable.call();
        }

        // 未执行到入口的，直接返回，不做任何处理
        if (!TraceMasterContext.isEnable()) {
            return callable.call();
        }

        // TODO 执行前置
        try {
            return callable.call();
        } finally {
            // TODO 执行后置
        }
    }


}
