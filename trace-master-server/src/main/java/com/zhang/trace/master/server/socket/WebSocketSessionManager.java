package com.zhang.trace.master.server.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * websocket 会话的管理以及消息的发送
 *
 * @author zhang
 * @date 2024-10-16 16:19
 */
@Slf4j
public class WebSocketSessionManager {

    /**
     * 保存 appId 以及对应的所有会话
     */
    private static final Map<String, List<WebSocketSession>> SESSION_HOLDER = new ConcurrentHashMap<>();

    /**
     * 保存 appId 以及对应的会话
     *
     * @param appId   需要监听的 appId
     * @param session 会话信息
     */
    public static void saveSession(String appId, WebSocketSession session) {
        List<WebSocketSession> sessions = SESSION_HOLDER.getOrDefault(appId, new CopyOnWriteArrayList<>());
        sessions.add(session);
        SESSION_HOLDER.put(appId, sessions);
    }

    /**
     * 获取 appId 对应的会话
     *
     * @param appId appId
     * @return appId 对应的会话
     */
    public static List<WebSocketSession> getSessions(String appId) {
        return Collections.unmodifiableList(SESSION_HOLDER.getOrDefault(appId, new ArrayList<>()));
    }

    /**
     * 移除 appId 以及对应的会话
     *
     * @param appId   需要监听的 appId
     * @param session 会话信息
     */
    public static void removeSession(String appId, WebSocketSession session) {
        List<WebSocketSession> sessions = SESSION_HOLDER.getOrDefault(appId, new CopyOnWriteArrayList<>());
        sessions.remove(session);
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
        getSessions(appId).forEach(session -> {
            sendMessage(session, textMessage);
        });
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
    public static void sendMessage(WebSocketSession session, TextMessage message) {
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            log.error("发送 socket 消息失败", e);
            throw new RuntimeException(e);
        }
    }

}
