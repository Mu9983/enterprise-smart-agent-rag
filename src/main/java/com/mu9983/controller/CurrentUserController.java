package com.mu9983.controller;

import com.mu9983.entity.Result;
import com.mu9983.entity.User;
import com.mu9983.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 获取当前用户
 */
@Slf4j
@RequestMapping
@RestController
public class CurrentUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/current-user")
    public Result currentUser() {
        log.info("查询当前用户信息");
        User currentUser = userService.currentUser();
        if (Objects.isNull(currentUser)) {
            return Result.error("未找到当前用户信息");
        }
        return Result.success(currentUser);
    }

}
