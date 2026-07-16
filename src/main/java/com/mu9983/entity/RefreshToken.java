package com.mu9983.entity;

/**
 * 用于redis存放refreshToken的常量类
 */
public class RefreshToken {
    // RefreshToken 有效期 12小时
    public static final long REFRESH_TTL = 60 * 12;
    // redis key
    public static final String REFRESH_TOKEN_KEY = "refresh:token:";
    public static final String REFRESH_USER_KEY = "refresh:user:";
}
