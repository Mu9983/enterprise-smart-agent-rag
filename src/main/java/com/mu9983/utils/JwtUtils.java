package com.mu9983.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private static final SecretKey KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    private static final long EXPIRATION = 1000 * 60 * 10; // 10分钟

    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims) // 自定义数据
                .issuedAt(new Date()) // 签发时间
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION)) // 过期时间
                .signWith(KEY) // 签名
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
