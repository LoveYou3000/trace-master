package com.zhang.trace.master.server.socket;

import com.zhang.trace.master.core.config.socket.response.domain.BaseResponse;
import com.zhang.trace.master.server.socket.response.AgentResponse;
import com.zhang.trace.master.server.utils.JacksonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket 会话的管理以及消息的发送
 *
 * @author zhang
 * @date 2024-10-16 16:19
 */
@Slf4j
public class WebSocketSessionManager {

    private static final int MAP_INIT_SIZE = 16;

    /**
     * 保存 appId 以及对应的所有会话
     */
    private static final Map<String, Map<String, WebSocketSession>> SESSION_HOLDER = new ConcurrentHashMap<>(MAP_INIT_SIZE);


    /**
     * 保存 appId 以及对应的会话
     *
     * @param appId      需要监听的 appId
     * @param instanceId 实例id
     * @param session    会话信息
     */
    public static void saveSession(String appId, String instanceId, WebSocketSession session) {
        Map<String, WebSocketSession> sessions = SESSION_HOLDER.getOrDefault(appId, new ConcurrentHashMap<>(MAP_INIT_SIZE));
        sessions.put(instanceId, session);
        SESSION_HOLDER.put(appId, sessions);
    }

    /**
     * 获取 appId 对应的会话
     *
     * @param appId appId
     * @return appId 对应的会话
     */
    public static List<WebSocketSession> getSessions(String appId) {
        Collection<WebSocketSession> sessions = SESSION_HOLDER.getOrDefault(appId, new ConcurrentHashMap<>(MAP_INIT_SIZE)).values();
        return List.copyOf(sessions);
    }

    /**
     * 移除 appId 以及对应的会话
     *
     * @param appId      需要监听的 appId
     * @param instanceId 实例id
     */
    @SneakyThrows(IOException.class)
    public static void removeSession(String appId, String instanceId) {
        Map<String, WebSocketSession> sessions = SESSION_HOLDER.getOrDefault(appId, new ConcurrentHashMap<>(MAP_INIT_SIZE));
        sessions.remove(instanceId).close();
        SESSION_HOLDER.put(appId, sessions);
    }

    /**
     * 向某个 appId 对应的所有会话广播消息
     *
     * @param appId   appId
     * @param message 要发送的消息
     */
    public static void broadcastMessage(String appId, String message) {
        TextMessage textMessage = new TextMessage(message);
        getSessions(appId).forEach(session -> sendMessage(session, textMessage));
    }

    /**
     * 向所有会话广播消息
     *
     * @param message 要发送的消息
     */
    public static void broadcastMessage(String message) {
        SESSION_HOLDER.forEach((appId, sessions) -> broadcastMessage(appId, message));
    }

    /**
     * 向某个会话发送消息
     *
     * @param appId           appId
     * @param instanceId      实例id
     * @param responseMessage 要发送的消息实体类
     */
    public static void sendMessage(String appId, String instanceId, BaseResponse responseMessage) {
        sendMessage(SESSION_HOLDER.getOrDefault(appId, new ConcurrentHashMap<>(MAP_INIT_SIZE)).get(instanceId), responseMessage);
    }

    /**
     * 向某个会话发送消息
     *
     * @param appId      appId
     * @param instanceId 实例id
     * @param message    要发送的消息
     */
    public static void sendMessage(String appId, String instanceId, String message) {
        sendMessage(SESSION_HOLDER.getOrDefault(appId, new ConcurrentHashMap<>(MAP_INIT_SIZE)).get(instanceId), message);
    }

    /**
     * 向某个会话发送消息
     *
     * @param appId      appId
     * @param instanceId 实例id
     * @param message    要发送的消息
     */
    public static void sendMessage(String appId, String instanceId, TextMessage message) {
        sendMessage(SESSION_HOLDER.getOrDefault(appId, new ConcurrentHashMap<>(MAP_INIT_SIZE)).get(instanceId), message);
    }

    /**
     * 向某个会话发送消息
     *
     * @param session         会话
     * @param responseMessage 要发送的消息实体类
     */
    public static void sendMessage(WebSocketSession session, BaseResponse responseMessage) {
        sendMessage(session, JacksonUtil.toJsonString(new AgentResponse<>(responseMessage)));
    }

    /**
     * 向某个会话发送消息
     *
     * @param session 会话
     * @param message 要发送的消息
     */
    public static void sendMessage(WebSocketSession session, String message) {
        sendMessage(session, new TextMessage(message));
    }

    /**
     * 向某个会话发送消息
     *
     * @param session 会话
     * @param message 要发送的消息
     */
    @SneakyThrows(IOException.class)
    public static void sendMessage(WebSocketSession session, TextMessage message) {
        session.sendMessage(message);
    }

}
