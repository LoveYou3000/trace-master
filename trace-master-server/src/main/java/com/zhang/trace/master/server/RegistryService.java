package com.zhang.trace.master.server;

import com.zhang.trace.master.core.config.socket.request.SocketMessage;
import com.zhang.trace.master.core.config.socket.request.SocketMessageType;
import com.zhang.trace.master.core.config.socket.request.domain.AgentEnableMessage;
import com.zhang.trace.master.core.config.socket.request.domain.RegistryMessage;
import com.zhang.trace.master.core.config.socket.request.domain.UnRegistryMessage;
import com.zhang.trace.master.server.domain.request.instance.ListRequest;
import com.zhang.trace.master.server.domain.request.instance.UpdateStatusRequest;
import com.zhang.trace.master.server.domain.response.base.Result;
import com.zhang.trace.master.server.domain.response.instance.ListResponse;
import com.zhang.trace.master.server.domain.response.base.ResultPage;
import com.zhang.trace.master.server.socket.WebSocketSessionManager;
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

    public void registry(RegistryMessage registryMessage) {
        int id = registryId.getAndIncrement();
        registryMessage.setId(id);
        registryMessage.setRegistryTime(Instant.now().toEpochMilli());

        registryMessages.add(registryMessage);
    }

    public void unRegistry(UnRegistryMessage unRegistryMessage) {
        registryMessages.removeIf(registryMessage -> Objects.equals(registryMessage.getAppId(), unRegistryMessage.getAppId()) &&
                Objects.equals(registryMessage.getInstanceId(), unRegistryMessage.getInstanceId()));
    }

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
        int startIdx = (listRequest.getCurrentPage() - 1) * listRequest.getPageSize();
        int endIdx = Math.min(startIdx + listRequest.getPageSize(), totalList.size());
        List<ListResponse> list = totalList.subList(startIdx, endIdx);
        return ResultPage.<ListResponse>builder()
                .total((long) totalList.size())
                .currentPage(listRequest.getCurrentPage())
                .pageSize(listRequest.getPageSize())
                .list(list)
                .build();
    }

    public void updateStatus(UpdateStatusRequest updateStatusRequest) {
        AgentEnableMessage agentEnableMessage = new AgentEnableMessage();
        agentEnableMessage.setAppId(updateStatusRequest.getAppId());
        agentEnableMessage.setInstanceId(updateStatusRequest.getInstanceId());
        agentEnableMessage.setEnable(updateStatusRequest.getStatus() == 1);

        SocketMessage<AgentEnableMessage> socketMessage = new SocketMessage<>(agentEnableMessage, SocketMessageType.AGENT_ENABLE);

        WebSocketSessionManager.sendMessage(updateStatusRequest.getAppId(), updateStatusRequest.getInstanceId(), socketMessage);

        registryMessages.stream().filter(registryMessage -> {
            return Objects.equals(registryMessage.getAppId(), updateStatusRequest.getAppId()) && Objects.equals(registryMessage.getInstanceId(), updateStatusRequest.getInstanceId());
        }).forEach(registryMessage -> registryMessage.setStatus(updateStatusRequest.getStatus()));
    }

}
