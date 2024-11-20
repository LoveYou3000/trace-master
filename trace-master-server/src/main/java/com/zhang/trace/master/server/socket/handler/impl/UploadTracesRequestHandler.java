package com.zhang.trace.master.server.socket.handler.impl;

import com.zhang.trace.master.core.socket.request.domain.UploadTracesMessage;
import com.zhang.trace.master.core.socket.request.domain.UploadTracesMessage.TraceMessage;
import com.zhang.trace.master.core.util.JacksonUtil;
import com.zhang.trace.master.server.socket.handler.AgentRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

/**
 * 上传调用链路信息处理
 *
 * @author zhang
 * @date 2024-11-10 18:58
 */
@Slf4j
public class UploadTracesRequestHandler implements AgentRequestHandler<UploadTracesMessage> {

    @Override
    public void handle(UploadTracesMessage uploadTracesMessage, WebSocketSession session) {
        Long traceId = uploadTracesMessage.getTraceId();
        List<TraceMessage> traces = uploadTracesMessage.getTraces();

        TraceMessage rootTrace = getRootTrace(traces);

        // TODO 保存 traceId 以及 其他信息
        // appId + instanceId -> List

        // TODO 保存 trace 链路信息
        // traceId -> List

        log.info("traceId:{}, trace:{}", traceId, JacksonUtil.toJsonString(traces));
    }

    private TraceMessage getRootTrace(List<TraceMessage> traceMessages) {
        return traceMessages.stream().filter(t -> t.getParentId() == 0).findFirst().orElseThrow();
    }

}
