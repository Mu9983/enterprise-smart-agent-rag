package com.mu9983.service.impl;

import com.mu9983.entity.LoginInfo;
import com.mu9983.entity.RefreshToken;
import com.mu9983.entity.User;
import com.mu9983.mapper.UserMapper;
import com.mu9983.service.UserService;
import com.mu9983.utils.JwtUtils;
import com.mu9983.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 登录接口
     */
    @Override
    public LoginInfo login(User user) {
        // 匹配登录信息
        User u = userMapper.selectByUsernameAndPassword(user);
        if (!u.getPassword().equals(user.getPassword())) {
            return null;
        }
        // 生成accessToken
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", u.getId());
        claims.put("username", u.getUsername());
        String accessToken = JwtUtils.generateToken(claims);
        // 生成 RefreshToken（UUID）
        String refreshToken = UUID.randomUUID().toString().replace("-", "");
        // 将refreshToken存入redis
        String redisKey = RefreshToken.REFRESH_TOKEN_KEY + refreshToken;
        String userKey = RefreshToken.REFRESH_USER_KEY + u.getId();
        // 双向存储，便于后续黑名单制作
        stringRedisTemplate.opsForValue().set(redisKey, userKey, Duration.ofMinutes(RefreshToken.REFRESH_TTL));
        stringRedisTemplate.opsForValue().set(userKey, redisKey, Duration.ofMinutes(RefreshToken.REFRESH_TTL));
        // 封装返回信息
        LoginInfo loginInfo = new LoginInfo();
        Map<String, String> token = new HashMap<>();
        token.put("access_token", accessToken);
        token.put("refresh_token", refreshToken);
        loginInfo.setToken(token);
        loginInfo.setUsername(u.getUsername());
        loginInfo.setId(u.getId());
        loginInfo.setPermission(u.getPermission());
        return loginInfo;
    }

    /**
     * 登出接口
     */
    @Override
    public void logout(Map<String, String> token) {
        String redisKey = RefreshToken.REFRESH_TOKEN_KEY + token.get("refresh_token");
        String userKey = RefreshToken.REFRESH_USER_KEY
                + JwtUtils.parseToken(token.get("access_token")).get("id").toString();
        stringRedisTemplate.delete(redisKey);
        stringRedisTemplate.delete(userKey);
        UserContext.removeToken();
    }

    /**
     * 获取当前用户信息
     */
    @Override
    public User currentUser() {
        // 获取当前用户token
        Map<String, String> token = UserContext.getToken();
        // 由token解码出id
        String accessToken = token.get("access_token");
        Integer userId = Integer.parseInt(JwtUtils.parseToken(accessToken).get("id").toString());
        return userMapper.selectById(userId);
    }

    /**
     * 刷新令牌
     */
    @Override
    public LoginInfo refreshToken(Map<String, String> token) {
        // 判断refreshToken是否存在
        String redisKey = RefreshToken.REFRESH_TOKEN_KEY + token.get("refresh_token");
        String userIdStr = stringRedisTemplate.opsForValue().get(redisKey);
        if (userIdStr == null) {
            return null;
        }
        // 判断用户是否存在
        User user = userMapper.selectById(Integer.parseInt(userIdStr.split(":")[2]));
        if (user == null) {
            stringRedisTemplate.delete(redisKey);
            stringRedisTemplate.delete(RefreshToken.REFRESH_USER_KEY + userIdStr);
            return null;
        }
        // 刷新令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        String newAccessToken = JwtUtils.generateToken(claims);
        // refreshToken滑动续期
        stringRedisTemplate.expire(redisKey, Duration.ofMinutes(RefreshToken.REFRESH_TTL));
        stringRedisTemplate.expire(RefreshToken.REFRESH_USER_KEY + userIdStr, Duration.ofMinutes(RefreshToken.REFRESH_TTL));
        // 封装返回信息
        LoginInfo loginInfo = new LoginInfo();
        Map<String, String> newToken = new HashMap<>();
        newToken.put("access_token", newAccessToken);
        newToken.put("refresh_token", token.get("refresh_token"));
        loginInfo.setToken(newToken);
        loginInfo.setUsername(user.getUsername());
        loginInfo.setId(user.getId());
        loginInfo.setPermission(user.getPermission());
        return loginInfo;
    }


}
