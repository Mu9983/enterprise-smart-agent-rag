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
        log.info("删除会话");
        try {
            chatService.delete(memoryId);
            log.info("删除会话记录：{}", memoryId);
        } catch (Exception e) {
            log.error("删除失败：{}", e.getMessage());
        }
        return Result.success();
    }

    @GetMapping("/record")
    public Result record(@RequestParam("memoryId") String memoryId) {
        log.info("获取对话历史");
        try {
            String record = chatService.getRecord(memoryId);
            log.info("获取对话记录：{}", record);
            return Result.success(record);
        } catch (Exception e) {
            log.error("获取对话记录失败：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }



}
