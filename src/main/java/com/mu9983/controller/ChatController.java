package com.mu9983.controller;

import com.mu9983.entity.Result;
import com.mu9983.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/assistant")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@RequestParam("memoryId") String memoryId,
                             @RequestParam("message") String message) {
        return chatService.streamChat(memoryId, message);
    }

    @DeleteMapping("/delete")
    public Result deleteRecord(@RequestParam("memoryId") String memoryId) {
        chatService.delete(memoryId);
        return Result.success();
    }

}
