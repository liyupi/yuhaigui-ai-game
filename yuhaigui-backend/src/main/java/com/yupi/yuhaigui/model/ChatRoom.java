package com.yupi.yuhaigui.model;

import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import lombok.Data;

import java.util.List;

/**
 * 聊天房间
 */
@Data
public class ChatRoom {

    private Long roomId;

    private List<ChatMessage> chatMessageList;
}
