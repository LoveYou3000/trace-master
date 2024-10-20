package com.zhang.trace.master.server.socket;

import com.zhang.trace.master.core.config.socket.request.ServerMessage;
import com.zhang.trace.master.core.config.socket.request.domain.BaseSocketMessage;
import com.zhang.trace.master.core.config.util.JacksonUtil;
import lombok.NonNull;
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
     * 保存 appId, instanceId 以及对应的会话
     */
    private static final Map<String, Map<String, WebSocketSession>> SESSION_HOLDER = new ConcurrentHashMap<>(MAP_INIT_SIZE);


    /**
     * 保存 appId, instanceId 以及对应的会话
     *
     * @param appId      需要监听的 appId
     * @param instanceId 实例id
     * @param session    会话信息
     */
    public static void saveSession(@NonNull String appId, @NonNull String instanceId, WebSocketSession session) {
        SESSION_HOLDER.computeIfAbsent(appId, k -> SESSION_HOLDER.put(k, new ConcurrentHashMap<>(MAP_INIT_SIZE)));
        Map<String, WebSocketSession> sessions = SESSION_HOLDER.get(appId);
        sessions.put(instanceId, session);
    }

    /**
     * 获取 appId 对应的所有会话
     *
     * @param appId appId
     * @return appId 对应的会话
     */
    public static List<WebSocketSession> getSessions(@NonNull String appId) {
        Collection<WebSocketSession> sessions = SESSION_HOLDER.getOrDefault(appId, new ConcurrentHashMap<>(MAP_INIT_SIZE)).values();
        return List.copyOf(sessions);
    }

    /**
     * 获取 appId 以及 instanceId 对应的会话
     *
     * @param appId      appId
     * @param instanceId 实例id
     * @return appId 对应的会话
     */
    public static WebSocketSession getSession(@NonNull String appId, @NonNull String instanceId) {
        return SESSION_HOLDER.getOrDefault(appId, new ConcurrentHashMap<>(MAP_INIT_SIZE)).get(instanceId);
    }

    /**
     * 移除 appId 以及对应的会话
     *
     * @param appId      需要监听的 appId
     * @param instanceId 实例id
     */
    @SneakyThrows(IOException.class)
    public static void removeSession(@NonNull String appId, @NonNull String instanceId) {
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
    public static void broadcastMessage(@NonNull String appId, @NonNull String message) {
        TextMessage textMessage = new TextMessage(message);
        getSessions(appId).forEach(session -> sendMessage(session, textMessage));
    }

    /**
     * 向某个 appId 对应的所有会话广播消息
     *
     * @param appId   appId
     * @param message 要发送的消息
     */
    public static void broadcastMessage(@NonNull String appId, @NonNull ServerMessage<? extends BaseSocketMessage> message) {
        TextMessage textMessage = new TextMessage(JacksonUtil.toJsonString(message));
        getSessions(appId).forEach(session -> sendMessage(session, textMessage));
    }

    /**
     * 向所有会话广播消息
     *
     * @param message 要发送的消息
     */
    public static void broadcastMessage(@NonNull String message) {
        SESSION_HOLDER.forEach((appId, sessions) -> broadcastMessage(appId, message));
    }

    /**
     * 向所有会话广播消息
     *
     * @param message 要发送的消息
     */
    public static void broadcastMessage(@NonNull ServerMessage<? extends BaseSocketMessage> message) {
        SESSION_HOLDER.forEach((appId, sessions) -> broadcastMessage(appId, message));
    }

    /**
     * 向某个会话发送消息
     *
     * @param appId         appId
     * @param instanceId    实例id
     * @param serverMessage 要发送的消息实体类
     */
    public static void sendMessage(@NonNull String appId, @NonNull String instanceId, @NonNull ServerMessage<? extends BaseSocketMessage> serverMessage) {
        sendMessage(SESSION_HOLDER.getOrDefault(appId, new ConcurrentHashMap<>(MAP_INIT_SIZE)).get(instanceId), serverMessage);
    }

    /**
     * 向某个会话发送消息
     *
     * @param appId      appId
     * @param instanceId 实例id
     * @param message    要发送的消息
     */
    public static void sendMessage(@NonNull String appId, @NonNull String instanceId, @NonNull String message) {
        sendMessage(SESSION_HOLDER.getOrDefault(appId, new ConcurrentHashMap<>(MAP_INIT_SIZE)).get(instanceId), message);
    }

    /**
     * 向某个会话发送消息
     *
     * @param appId      appId
     * @param instanceId 实例id
     * @param message    要发送的消息
     */
    public static void sendMessage(@NonNull String appId, @NonNull String instanceId, @NonNull TextMessage message) {
        sendMessage(getSession(appId, instanceId), message);
    }

    /**
     * 向某个会话发送消息
     *
     * @param session       会话
     * @param serverMessage 要发送的消息实体类
     */
    public static void sendMessage(@NonNull WebSocketSession session, @NonNull ServerMessage<? extends BaseSocketMessage> serverMessage) {
        sendMessage(session, JacksonUtil.toJsonString(serverMessage));
    }

    /**
     * 向某个会话发送消息
     *
     * @param session 会话
     * @param message 要发送的消息
     */
    public static void sendMessage(@NonNull WebSocketSession session, @NonNull String message) {
        sendMessage(session, new TextMessage(message));
    }

    /**
     * 向某个会话发送消息
     *
     * @param session 会话
     * @param message 要发送的消息
     */
    @SneakyThrows(IOException.class)
    public static void sendMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) {
        session.sendMessage(message);
    }

}
