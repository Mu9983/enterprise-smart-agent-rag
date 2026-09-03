package com.mu9983.service;

import reactor.core.publisher.Flux;

public interface ChatService {

    Flux<String> streamChat(String memoryId, String message);

    void delete(String memoryId);
}
