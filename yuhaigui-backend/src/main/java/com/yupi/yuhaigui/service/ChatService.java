package com.yupi.yuhaigui.service;

import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.yupi.yuhaigui.model.ChatRoom;

import java.util.List;
import java.util.Map;

/**
 * 聊天服务
 */
public interface ChatService {

    /**
     * 和 AI 对话
     *
     * @param roomId 聊天室 id
     * @param message 用户自己输入的消息
     * @return AI 的结果
     */
    String doChat(long roomId, String message);

    /**
     * 获取对话列表
     *
     * @return 聊天室列表
     */
    List<ChatRoom> getChatRoomList();
}
