package com.zhang.trace.master.server.controller;

import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
import com.zhang.trace.master.server.domain.request.instance.ConfigRequest;
import com.zhang.trace.master.server.domain.request.instance.ListRequest;
import com.zhang.trace.master.server.domain.request.instance.UpdateStatusRequest;
import com.zhang.trace.master.server.domain.response.base.Result;
import com.zhang.trace.master.server.domain.response.base.ResultTable;
import com.zhang.trace.master.server.domain.response.instance.ListResponse;
import com.zhang.trace.master.server.service.ConfigService;
import com.zhang.trace.master.server.service.RegistryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实例管理 controller
 *
 * @author zhang
 * @date 2024-11-10 12:25
 */
@RestController
@RequestMapping("instances")
@Slf4j
@RequiredArgsConstructor
public class InstanceMgntController {

    private final RegistryService registryService;

    private final ConfigService configService;

    @PostMapping("list")
    public ResultTable<ListResponse> list(@RequestBody ListRequest listRequest) {
        return ResultTable.<ListResponse>builder()
                .success(true)
                .data(registryService.list(listRequest))
                .build();
    }

    @PostMapping("updateStatus")
    public Result<Void> updateStatus(@RequestBody UpdateStatusRequest updateStatusRequest) {
        registryService.updateStatus(updateStatusRequest);
        return Result.<Void>builder()
                .success(true).build();
    }

    @PostMapping("config")
    public Result<TraceMasterAgentConfig> config(@RequestBody ConfigRequest configRequest) {
        return Result.<TraceMasterAgentConfig>builder()
                .success(true)
                .data(configService.getConfig(configRequest.getAppId()))
                .build();
    }

}
