package com.mu9983.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 用于确认登录信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {
    private Integer id;                 // id
    private String username;            // 用户名
    private String password;            // 密码
    private Map<String, String> token;  // 令牌
}
