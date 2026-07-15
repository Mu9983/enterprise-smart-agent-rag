package com.mu9983.controller;

import com.mu9983.entity.LoginInfo;
import com.mu9983.entity.Result;
import com.mu9983.entity.User;
import com.mu9983.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1")
@RestController
public class LoginController {

    @Autowired
    private UserService userService;


    @PostMapping("/auth/login")
    public Result login(@RequestBody User user){
        log.info("登录");
        LoginInfo loginInfo = userService.Login(user);
        if(loginInfo != null) {
            return Result.success(loginInfo);
        }
        return Result.error("用户名或密码错误");
    }
}
