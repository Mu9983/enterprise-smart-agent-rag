package com.mu9983.service.impl;

import com.mu9983.repository.RedisChatMemoryStore;
import com.mu9983.service.AiService;
import com.mu9983.service.ChatService;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private AiService aiService;
    @Autowired
    private ChatMemoryProvider chatMemoryProvider;
    @Autowired
    private RedisChatMemoryStore redisChatMemoryStore;

    /**
     * ai对话方法
     * @param memoryId 对话记录，时间戳+userId
     * @param message 用户消息
     */
    @Override
    public Flux<String> streamChat(String memoryId, String message) {
        return aiService.chat(memoryId, message);
    }

    /**
     * 删除对话记录
     * @param memoryId 对话id
     */
    @Override
    public void delete(String memoryId) {
        ChatMemory chatMemory = chatMemoryProvider.get(memoryId);
        if (chatMemory != null) {
            chatMemory.clear();
        }
    }

    /**
     * 获取对话记录
     * @param memoryId 对话id
     * @return 对话记录
     */
    @Override
    public String getRecord(String memoryId) {
        return redisChatMemoryStore.getMessagesAsJson(memoryId);
    }
}
