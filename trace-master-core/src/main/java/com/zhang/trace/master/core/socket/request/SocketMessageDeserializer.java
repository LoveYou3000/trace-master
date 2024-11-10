package com.zhang.trace.master.core.socket.request;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.zhang.trace.master.core.socket.request.domain.BaseSocketMessage;
import com.zhang.trace.master.core.util.JacksonUtil;

import java.io.IOException;

/**
 * socketMessage 自定义反序列器
 *
 * @author zhang
 * @date 2024-10-21 17:06
 */
public class SocketMessageDeserializer extends JsonDeserializer<SocketMessage<?>> {

    @Override
    public SocketMessage<?> deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);

        // 获取 type 字段并解析为 MessageType
        String typeValue = node.get("type").asText();
        SocketMessageType messageType = SocketMessageType.valueOf(typeValue.toUpperCase());

        // 获取 data 字段并根据 type 的 paramKlz 转换
        JsonNode dataNode = node.get("data");
        Class<? extends BaseSocketMessage> paramKlz = messageType.getRequestKlz();

        // 使用 ObjectMapper 将 data 反序列化为相应的类型
        BaseSocketMessage data = JacksonUtil.parseTreeNodeObj(dataNode, paramKlz);

        // 创建并返回 Message 对象
        return new SocketMessage<>(data, messageType);
    }

}
