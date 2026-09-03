package com.mu9983.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@dev.langchain4j.service.spring.AiService(
        wiringMode = AiServiceWiringMode.AUTOMATIC,
        streamingChatModel = "ollamaStreamingChatModel",
        chatMemoryProvider = "chatMemoryProvider"
)
public interface AiService {
    @SystemMessage(fromResource = "system.txt")
    Flux<String> chat(@MemoryId String memoryId, @UserMessage String message);
}
