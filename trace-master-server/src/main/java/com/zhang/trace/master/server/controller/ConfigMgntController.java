package com.zhang.trace.master.server.controller;

import com.zhang.trace.master.server.domain.request.config.ListRequest;
import com.zhang.trace.master.server.domain.request.config.UpdateRequest;
import com.zhang.trace.master.server.domain.response.base.Result;
import com.zhang.trace.master.server.domain.response.base.ResultTable;
import com.zhang.trace.master.server.domain.response.config.DefaultConfigResponse;
import com.zhang.trace.master.server.domain.response.config.ListResponse;
import com.zhang.trace.master.server.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置管理 controller
 *
 * @author zhang
 * @date 2024-11-13 16:28
 */
@RestController
@RequestMapping("configs")
public class ConfigMgntController {

    @Autowired
    private ConfigService configService;

    @PostMapping("list")
    public ResultTable<ListResponse> list(@RequestBody ListRequest listRequest) {
        return ResultTable.<ListResponse>builder()
                .success(true)
                .data(configService.list(listRequest))
                .build();
    }

    @PostMapping("defaultConfig")
    public Result<DefaultConfigResponse> defaultConfig() {
        DefaultConfigResponse defaultConfigResponse = new DefaultConfigResponse();
        defaultConfigResponse.setDefaultConfig(configService.getDefaultConfig());
        return Result.<DefaultConfigResponse>builder()
                .success(true)
                .data(defaultConfigResponse).build();
    }

    @PostMapping("update")
    public Result<Void> update(@RequestBody UpdateRequest updateRequest) {
        configService.updateConfig(updateRequest);
        return Result.<Void>builder()
                .success(true).build();
    }

    @PostMapping("updateDefaultConfig")
    public Result<Void> updateDefaultConfig(@RequestBody UpdateRequest updateRequest) {
        configService.updateDefaultConfig(updateRequest);
        return Result.<Void>builder()
                .success(true).build();
    }

}
