package com.zhang.trace.master.core.config;

import lombok.Data;

import java.util.Set;

/**
 * trace-master agent配置类
 *
 * @author zhang
 * @date 2024-10-15 19:54
 */
@Data
public class TraceMasterAgentConfig {

    private Set<String> includePackages;

    private Set<String> excludePackages;

}
