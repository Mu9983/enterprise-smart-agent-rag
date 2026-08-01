package com.mu9983.controller;

import com.mu9983.entity.Result;
import com.mu9983.entity.User;
import com.mu9983.entity.VerifyUserPassword;
import com.mu9983.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 获取当前用户
 */
@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {

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

    @PostMapping("/verify")
    public Result verify(@RequestBody VerifyUserPassword verifyUserPassword) {
        log.info("验证身份");
        boolean verifyPassword = userService.verifyPassword(verifyUserPassword);
        if (verifyPassword) {
            return Result.success();
        }
        return Result.error("验证失败");
    }

    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        log.info("修改用户信息");
        userService.changeUser(user);
        return Result.success(user);
    }


}
