package com.zhang.trace.master.server.domain.response.config;

import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
import lombok.Data;

/**
 * 配置管理-配置列表响应
 *
 * @author zhang
 * @date 2024-11-13 16:27
 */
@Data
public class ListResponse {

    private String appId;

    private TraceMasterAgentConfig config;

}
