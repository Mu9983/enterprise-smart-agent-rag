package com.mu9983.controller;

import com.mu9983.aiservice.ConsultantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping
public class ChatController {

    @Autowired
    private ConsultantService consultantService;

    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@RequestParam("message") String message) {
        return consultantService.chat(message);
    }

}
