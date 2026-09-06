package com.mu9983.controller;

import com.mu9983.entity.Result;
import com.mu9983.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public Result home() {
        log.info("首页更新头像");
        return Result.success(userService.currentUser());
    }

}
