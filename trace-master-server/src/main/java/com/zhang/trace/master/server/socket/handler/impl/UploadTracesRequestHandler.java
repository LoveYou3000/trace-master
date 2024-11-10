package com.zhang.trace.master.server.socket.handler.impl;

import com.zhang.trace.master.core.socket.request.domain.UploadTracesMessage;
import com.zhang.trace.master.core.util.JacksonUtil;
import com.zhang.trace.master.server.socket.handler.AgentRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

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
        UploadTracesMessage.TraceMessage rootTrace = uploadTracesMessage.getRootTrace();

        log.info("traceId:{}, trace:{}", traceId, JacksonUtil.toJsonString(rootTrace));
    }

}
