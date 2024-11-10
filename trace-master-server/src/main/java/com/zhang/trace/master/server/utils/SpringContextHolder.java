package com.zhang.trace.master.server.utils;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * spring 上下文获取
 *
 * @author zhang
 * @date 2024-10-16 17:37
 */
@Component
@Getter
public class SpringContextHolder {

    @Getter
    private static ApplicationContext springCtx;

    @Autowired
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.springCtx = applicationContext;
    }

}
