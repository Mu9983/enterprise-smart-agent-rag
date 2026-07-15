package com.mu9983.entity;

/**
 * 用于redis存储jwt令牌的产量类，令牌存储时间30分钟
 */
public class AuthConstant {
    // Redis key前缀
    public static final String LOGIN_TOKEN_PREFIX = "login:token:";
    // Token有效时长：30分钟
    public static final long TOKEN_EXPIRE_MINUTES = 30;
}