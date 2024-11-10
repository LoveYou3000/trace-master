package com.zhang.trace.master.server.domain.response.instance;

import lombok.Data;

/**
 * 实例管理-实例列表响应
 *
 * @author zhang
 * @date 2024-11-10 12:39
 */
@Data
public class ListResponse {

    private Integer id;

    private String appId;

    private String instanceId;

    private String ip;

    private Integer status;

    private String system;

    private String javaVersion;

    private Long registryTime;

}
