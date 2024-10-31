package com.zhang.trace.master.agent.interceptor;

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
        Class<?> klz = method.getDeclaringClass();

        try {
            return callable.call();
        } finally {
        }
    }


}
