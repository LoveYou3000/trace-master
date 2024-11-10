package com.zhang.trace.master.agent.interceptor;

import com.zhang.trace.master.agent.interceptor.context.TraceMasterContext;
import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
import com.zhang.trace.master.core.config.util.MatchUtil;
import io.opentracing.mock.MockSpan;
import io.opentracing.mock.MockTracer;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * trace master 方法增强
 *
 * @author zhang
 * @date 2024-10-24 09:23
 */
@Slf4j
public class TraceMethodInterceptor {

    @RuntimeType
    public static <T> T interceptor(@Origin Method method, @AllArguments Object[] args, @SuperCall Callable<T> callable) throws Exception {
        // 全局未启用时，直接返回，不做任何处理
        if (!TraceMasterContext.isGlobalEnable()) {
            return callable.call();
        }

        TraceMasterAgentConfig config = TraceMasterContext.getTraceMasterAgentConfig();
        // 类名及方法名不匹配，直接返回，不做任何处理
        Class<?> klz = method.getDeclaringClass();
        String klzName = klz.getName();
        String methodName = method.getName();
        String argTypes = Arrays.stream(method.getParameterTypes()).map(Class::getSimpleName).collect(Collectors.joining(","));
        if (!MatchUtil.classMatch(klzName, config) || !MatchUtil.methodMatch(methodName)) {
            return callable.call();
        }

        if (!TraceMasterContext.isEnable()) {
            // 执行到入口的，设置入口标识，继续执行
            if (MatchUtil.entranceMatch(klzName, methodName, config)) {
                TraceMasterContext.setEnable(true);
            } else {
                // 未执行到入口的，直接返回，不做任何处理
                return callable.call();
            }
        }

        // 执行前置
        MockTracer tracer = TraceMasterContext.getTracer();
        MockTracer.SpanBuilder spanBuilder = tracer.buildSpan(String.format("%s.%s(%s)", klzName, methodName, argTypes));
        MockSpan parentSpan = TraceMasterContext.getSpan();
        if (Objects.nonNull(parentSpan)) {
            // 设置父子关系
            spanBuilder.asChildOf(parentSpan);
        }
        MockSpan span = spanBuilder.start();
        TraceMasterContext.setSpan(span);
        try {
            return callable.call();
        } finally {
            // 执行后置
            span.finish();
            if (Objects.isNull(parentSpan)) {
                List<MockSpan> finishedSpans = tracer.finishedSpans();
                TraceMasterContext.getAgentSocketClient().uploadFinishedSpans(finishedSpans);
                tracer.close();
                TraceMasterContext.clear();
            } else {
                TraceMasterContext.setSpan(parentSpan);
            }
        }
    }


}
