package com.mu9983.controller;

import com.mu9983.entity.LoginInfo;
import com.mu9983.service.UserService;
import com.mu9983.utils.UserContext;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping
@RestController
public class RefreshController {

    @Autowired
    private UserService userService;

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh() {
        log.info("刷新令牌");
        LoginInfo newToken = userService.refreshToken(UserContext.getToken());
        if (newToken == null) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("登录信息异常，请重新登录");
        }
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(newToken);
    }

}
