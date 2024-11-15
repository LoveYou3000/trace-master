package com.zhang.trace.master.server.service;

import com.zhang.trace.master.core.socket.request.SocketMessage;
import com.zhang.trace.master.core.socket.request.SocketMessageType;
import com.zhang.trace.master.core.socket.request.domain.AgentEnableMessage;
import com.zhang.trace.master.core.socket.request.domain.RegistryMessage;
import com.zhang.trace.master.core.socket.request.domain.UnRegistryMessage;
import com.zhang.trace.master.server.domain.request.instance.ListRequest;
import com.zhang.trace.master.server.domain.request.instance.UpdateStatusRequest;
import com.zhang.trace.master.server.domain.response.base.ResultPage;
import com.zhang.trace.master.server.domain.response.instance.ListResponse;
import com.zhang.trace.master.server.socket.WebSocketSessionManager;
import com.zhang.trace.master.server.utils.PageUtil;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * agent 注册业务
 *
 * @author zhang
 * @date 2024-11-10 12:14
 */
@Service
public class RegistryService {

    /**
     * 注册信息的自增 id
     */
    private final AtomicInteger registryId = new AtomicInteger(1);

    /**
     * 保存所有的注册信息
     */
    @Getter
    private final List<RegistryMessage> registryMessages = new CopyOnWriteArrayList<>();

    /**
     * 注册
     *
     * @param registryMessage 注册的消息
     */
    public void registry(RegistryMessage registryMessage) {
        int id = registryId.getAndIncrement();
        registryMessage.setId(id);
        registryMessage.setRegistryTime(Instant.now().toEpochMilli());

        registryMessages.add(registryMessage);
    }

    /**
     * 反注册
     *
     * @param unRegistryMessage 反注册的消息
     */
    public void unRegistry(UnRegistryMessage unRegistryMessage) {
        registryMessages.removeIf(registryMessage -> Objects.equals(registryMessage.getAppId(), unRegistryMessage.getAppId()) &&
                Objects.equals(registryMessage.getInstanceId(), unRegistryMessage.getInstanceId()));
    }

    /**
     * 分页查询
     *
     * @param listRequest 分页查询请求
     * @return 分页查询结果
     */
    public ResultPage<ListResponse> list(ListRequest listRequest) {
        List<ListResponse> totalList = registryMessages
                .stream()
                .filter(registryMessage -> {
                    if (StringUtils.hasLength(listRequest.getAppId())) {
                        return Objects.equals(registryMessage.getAppId(), listRequest.getAppId());
                    }
                    return true;
                })
                .filter(registryMessage -> {
                    if (Objects.nonNull(listRequest.getStatus())) {
                        return Objects.equals(registryMessage.getStatus(), listRequest.getStatus());
                    }
                    return true;
                })
                .sorted((o1, o2) -> o2.getRegistryTime().compareTo(o1.getRegistryTime()))
                .map(o -> {
                    ListResponse resp = new ListResponse();
                    BeanUtils.copyProperties(o, resp);
                    return resp;
                })
                .toList();

        return PageUtil.page(totalList, listRequest);
    }

    /**
     * 更新状态
     *
     * @param updateStatusRequest 更新状态请求
     */
    public void updateStatus(UpdateStatusRequest updateStatusRequest) {
        AgentEnableMessage agentEnableMessage = new AgentEnableMessage();
        agentEnableMessage.setAppId(updateStatusRequest.getAppId());
        agentEnableMessage.setInstanceId(updateStatusRequest.getInstanceId());
        agentEnableMessage.setEnable(updateStatusRequest.getStatus() == 1);

        SocketMessage<AgentEnableMessage> socketMessage = new SocketMessage<>(agentEnableMessage, SocketMessageType.AGENT_ENABLE);

        WebSocketSessionManager.sendMessage(updateStatusRequest.getAppId(), updateStatusRequest.getInstanceId(), socketMessage);

        registryMessages.stream().filter(registryMessage -> {
            return Objects.equals(registryMessage.getAppId(), updateStatusRequest.getAppId()) &&
                    Objects.equals(registryMessage.getInstanceId(), updateStatusRequest.getInstanceId());
        }).forEach(registryMessage -> registryMessage.setStatus(updateStatusRequest.getStatus()));
    }

}
