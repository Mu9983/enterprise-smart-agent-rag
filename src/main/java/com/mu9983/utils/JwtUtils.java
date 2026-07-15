//package com.mu9983.utils;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//import java.util.Map;
//
//public class JwtUtils {
//    private static final SecretKey KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
//    private static final long EXPIRATION = 1000 * 60 * 10; // 10分钟
//
//    public static String generateToken(Map<String, Object> claims) {
//        return Jwts.builder()
//                .claims(claims) // 自定义数据
//                .issuedAt(new Date()) // 签发时间
//                .expiration(new Date(System.currentTimeMillis() + EXPIRATION)) // 过期时间
//                .signWith(KEY) // 签名
//                .compact();
//    }
//
//    public static Claims parseToken(String token) {
//        return Jwts.parser()
//                .verifyWith(KEY)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//}
package com.mu9983.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    // 固定密钥：长度至少32个字符（HS256要求），自行修改为自己的字符串
    private static final String SECRET_STR = "mu9983-jwt-secret-key-20260715-long-text-32chars";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_STR.getBytes(StandardCharsets.UTF_8));

    // JWT自身有效期：10分钟
    private static final long EXPIRATION = 1000 * 60 * 10;

    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(KEY)
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