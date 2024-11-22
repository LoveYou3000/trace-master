package com.zhang.trace.master.server.domain.response.config;

import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
import lombok.Data;

/**
 * 配置管理-默认配置响应
 *
 * @author zhang
 * @date 2024-11-22 09:00
 */
@Data
public class DefaultConfigResponse {

    private TraceMasterAgentConfig defaultConfig;

}
