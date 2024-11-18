package com.zhang.trace.master.server.service;

import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
import com.zhang.trace.master.server.domain.request.config.ListRequest;
import com.zhang.trace.master.server.domain.request.config.UpdateRequest;
import com.zhang.trace.master.server.domain.response.base.ResultPage;
import com.zhang.trace.master.server.domain.response.config.ListResponse;
import com.zhang.trace.master.server.utils.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * agent 配置管理业务
 *
 * @author zhang
 * @date 2024-11-13 08:45
 */
@Service
@RequiredArgsConstructor
public class ConfigService {

    private final Map<String, TraceMasterAgentConfig> configMap = new ConcurrentHashMap<>();

    private final TraceMasterAgentConfig defaultConfig;

    public ResultPage<ListResponse> list(ListRequest listRequest) {
        List<ListResponse> totalList = configMap
                .entrySet()
                .stream()
                .filter(configEntry -> {
                    if (StringUtils.hasLength(listRequest.getAppId())) {
                        return Objects.equals(configEntry.getKey(), listRequest.getAppId());
                    }
                    return true;
                })
                .sorted((o1, o2) -> o2.getValue().getLastUpdate().compareTo(o1.getValue().getLastUpdate()))
                .map(o -> {
                    ListResponse resp = new ListResponse();
                    BeanUtils.copyProperties(o, resp);
                    return resp;
                })
                .toList();

        return PageUtil.page(totalList, listRequest);
    }

    public void updateConfig(UpdateRequest updateRequest) {
        configMap.put(updateRequest.getAppId(), updateRequest.getConfig());
    }

    public void updateDefaultConfig(UpdateRequest updateRequest) {
        BeanUtils.copyProperties(updateRequest.getConfig(), defaultConfig);
    }

    public TraceMasterAgentConfig getConfig(String appId) {
        return configMap.computeIfAbsent(appId, k -> defaultConfig);
    }

}
