package com.mu9983.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;         // id
    private String username;    // 用户名
    private String password;    // 密码
    private String gender;      // 性别
    private Integer age;        // 年龄
    private String email;       // 邮箱
    private String address;     // 地址
    private Integer permission; // 权限
}
