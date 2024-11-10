package com.zhang.trace.master.core.util;

import lombok.NonNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池的线程工厂，负责线程的命名
 *
 * @author zhang
 * @date 2024/10/28 14:57
 */
public class NamedThreadFactory implements ThreadFactory {

    private final ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final String threadPrefix;

    private final boolean daemon;

    public NamedThreadFactory(String threadPrefix) {
        this.threadPrefix = threadPrefix;
        this.daemon = false;
    }

    public NamedThreadFactory(String threadPrefix, boolean daemon) {
        this.threadPrefix = threadPrefix;
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(@NonNull Runnable runnable) {
        Thread thread = this.defaultThreadFactory.newThread(runnable);
        thread.setDaemon(this.daemon);
        thread.setName(this.threadPrefix + "-" + this.threadNumber);
        return thread;
    }
}