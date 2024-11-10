package com.zhang.trace.master.server.controller;

import com.zhang.trace.master.server.RegistryService;
import com.zhang.trace.master.server.domain.request.instance.ListRequest;
import com.zhang.trace.master.server.domain.request.instance.UpdateStatusRequest;
import com.zhang.trace.master.server.domain.response.base.Result;
import com.zhang.trace.master.server.domain.response.instance.ListResponse;
import com.zhang.trace.master.server.domain.response.base.ResultTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
public class InstanceMgntController {

    @Autowired
    private RegistryService registryService;

    @PostMapping
    public ResultTable<ListResponse> list(@RequestBody ListRequest listRequest) {
        return ResultTable.<ListResponse>builder()
                .success(true)
                .data(registryService.list(listRequest))
                .build();
    }

    @PutMapping
    public Result<Void> updateStatus(@RequestBody UpdateStatusRequest updateStatusRequest) {
        registryService.updateStatus(updateStatusRequest);
        return Result.<Void>builder()
                .success(true).build();
    }

}
