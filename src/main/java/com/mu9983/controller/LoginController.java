package com.mu9983.controller;

import com.mu9983.entity.LoginInfo;
import com.mu9983.entity.Result;
import com.mu9983.entity.User;
import com.mu9983.service.UserService;
import com.mu9983.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping
@RestController
public class LoginController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public Result login(@RequestBody User user){
        log.info("登录");
        LoginInfo loginInfo = userService.login(user);
        if(loginInfo != null) {
            return Result.success(loginInfo);
        }
        return Result.error("用户名或密码错误");
    }

    @PostMapping("/logout")
    public Result logout(){
        log.info("登出");
        userService.logout(UserContext.getToken());
        return Result.success("登出成功");
    }
}
