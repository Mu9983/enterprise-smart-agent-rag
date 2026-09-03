package com.mu9983.service.impl;

import com.mu9983.service.AiService;
import com.mu9983.service.ChatService;
import dev.langchain4j.data.message.AiMessage;
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

    @Override
    public Flux<String> streamChat(String memoryId, String message) {
        ChatMemory chatMemory = chatMemoryProvider.get(memoryId);
        StringBuilder sb = new StringBuilder();

        return aiService.chat(memoryId, message)
                .doOnNext(sb::append)
                .doOnComplete(() -> chatMemory.add(AiMessage.from(sb.toString())))
                .doOnError(err -> {
                    String errMsg = err.getMessage() != null ? err.getMessage() : "未知异常";
                    chatMemory.add(AiMessage.from("[生成失败] " + errMsg));
                });
    }

    @Override
    public void delete(String memoryId) {
        ChatMemory chatMemory = chatMemoryProvider.get(memoryId);
        if (chatMemory != null) {
            chatMemory.clear();
        }
    }
}
