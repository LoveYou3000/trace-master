package com.zhang.trace.master.server.config;

import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * trace-master 配置
 *
 * @author zhang
 * @date 2024-11-18 16:23
 */
@Configuration
public class TraceMasterConfig {

    @Bean
    public TraceMasterAgentConfig defaultConfig() {
        return new TraceMasterAgentConfig();
    }

}
