package com.mu9983.aiservice;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.AUTOMATIC,
        streamingChatModel = "ollamaStreamingChatModel"
)
public interface ConsultantService {

//    String chat(String message);

    Flux<String> chat(String message);
}
