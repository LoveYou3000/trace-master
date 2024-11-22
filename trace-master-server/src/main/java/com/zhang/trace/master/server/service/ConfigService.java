package com.zhang.trace.master.server.service;

import com.zhang.trace.master.core.config.TraceMasterAgentConfig;
import com.zhang.trace.master.core.consts.RedisConstants;
import com.zhang.trace.master.server.domain.request.config.ListRequest;
import com.zhang.trace.master.server.domain.request.config.UpdateRequest;
import com.zhang.trace.master.server.domain.response.base.ResultPage;
import com.zhang.trace.master.server.domain.response.config.ListResponse;
import com.zhang.trace.master.server.utils.PageUtil;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * agent 配置管理业务
 *
 * @author zhang
 * @date 2024-11-13 08:45
 */
@Service
@RequiredArgsConstructor
public class ConfigService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Getter
    private TraceMasterAgentConfig defaultConfig;

    @PostConstruct
    public void init() {
        redisTemplate.opsForValue().setIfAbsent(RedisConstants.CONFIG_DEFAULT, new TraceMasterAgentConfig());
        this.defaultConfig = (TraceMasterAgentConfig) redisTemplate.opsForValue().get(RedisConstants.CONFIG_DEFAULT);
    }

    public ResultPage<ListResponse> list(ListRequest listRequest) {
        Map<Object, Object> configMap = redisTemplate.opsForHash().entries(RedisConstants.CONFIG_LIST);
        List<ListResponse> totalList = configMap
                .entrySet()
                .stream()
                .filter(configEntry -> {
                    if (StringUtils.hasLength(listRequest.getAppId())) {
                        return Objects.equals(configEntry.getKey(), listRequest.getAppId());
                    }
                    return true;
                })
                .sorted((o1, o2) -> ((TraceMasterAgentConfig) o2.getValue()).getLastUpdate().compareTo(((TraceMasterAgentConfig) o1.getValue()).getLastUpdate()))
                .map(o -> {
                    ListResponse resp = new ListResponse();
                    resp.setAppId((String) o.getKey());
                    resp.setConfig((TraceMasterAgentConfig) o.getValue());
                    return resp;
                })
                .toList();

        return PageUtil.page(totalList, listRequest);
    }

    public void updateConfig(UpdateRequest updateRequest) {
        redisTemplate.opsForHash().put(RedisConstants.CONFIG_LIST, updateRequest.getAppId(), updateRequest.getConfig());
    }

    public void updateDefaultConfig(UpdateRequest updateRequest) {
        defaultConfig = updateRequest.getConfig();
        redisTemplate.opsForValue().set(RedisConstants.CONFIG_DEFAULT, defaultConfig);
    }

    public TraceMasterAgentConfig getConfig(String appId) {
        if (!redisTemplate.opsForHash().hasKey(RedisConstants.CONFIG_LIST, appId)) {
            redisTemplate.opsForHash().put(RedisConstants.CONFIG_LIST, appId, defaultConfig);
        }
        return (TraceMasterAgentConfig) redisTemplate.opsForHash().get(RedisConstants.CONFIG_LIST, appId);
    }

}
