package com.zhang.trace.master.core.config.socket.request;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.zhang.trace.master.core.config.socket.request.domain.BaseRequest;
import com.zhang.trace.master.core.config.util.JacksonUtil;

import java.io.IOException;

/**
 * AgentRequest 的反序列
 *
 * @author zhang
 * @date 2024-10-18 10:20
 */
public class AgentRequestDeserializer extends JsonDeserializer<AgentRequest<?>> {

    @Override
    public AgentRequest<?> deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);

        // 获取 type 字段并解析为 MessageType
        String typeValue = node.get("type").asText();
        AgentRequestType messageType = AgentRequestType.valueOf(typeValue.toUpperCase());

        // 获取 data 字段并根据 type 的 paramKlz 转换
        JsonNode dataNode = node.get("data");
        Class<? extends BaseRequest> paramKlz = messageType.getRequestKlz();

        // 使用 ObjectMapper 将 data 反序列化为相应的类型
        BaseRequest data = JacksonUtil.parseTreeNodeObj(dataNode, paramKlz);

        // 创建并返回 Message 对象
        AgentRequest<BaseRequest> message = new AgentRequest<>();
        message.setData(data);
        message.setType(messageType);

        return message;
    }

}
