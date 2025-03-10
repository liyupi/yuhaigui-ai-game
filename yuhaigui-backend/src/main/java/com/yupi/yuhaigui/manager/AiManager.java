package com.yupi.yuhaigui.manager;

import cn.hutool.core.collection.CollUtil;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionChoice;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * AI 调用工具类
 */
@Service
public class AiManager {

    // AI 调用客服端
    @Resource
    private ArkService arkService;

    /**
     * 聊天（只允许传入系统预设和用户预设）
     *
     * @param systemPrompt
     * @param userPrompt
     * @return
     */
    public String doChat(String systemPrompt, String userPrompt) {
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content(systemPrompt).build();
        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(userPrompt).build();
        messages.add(systemMessage);
        messages.add(userMessage);
        return doChat(messages);
    }

    /**
     * 更通用的方法，允许用户传入任意条消息列表
     *
     * @param chatMessageList
     * @return
     */
    public String doChat(List<ChatMessage> chatMessageList) {
        // 单次调用
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                // 指定您创建的方舟推理接入点 ID，此处已帮您修改为您的推理接入点 ID
                .model("deepseek-v3-241226")
                .messages(chatMessageList)
                .build();

        List<ChatCompletionChoice> choiceList = arkService.createChatCompletion(chatCompletionRequest)
                .getChoices();
        if (CollUtil.isEmpty(choiceList)) {
            throw new RuntimeException("AI 没有返回任何内容");
        }
        String content = (String) choiceList.get(0).getMessage().getContent();
        System.out.println("AI 返回内容：" + content);
        return content;
    }
}
