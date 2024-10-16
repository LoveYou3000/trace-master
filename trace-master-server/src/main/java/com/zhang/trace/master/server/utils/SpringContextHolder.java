package com.zhang.trace.master.server.utils;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spring 上下文获取
 *
 * @author zhang
 * @date 2024-10-16 17:37
 */
@Getter
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext springCtx;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.springCtx = applicationContext;
    }

}
