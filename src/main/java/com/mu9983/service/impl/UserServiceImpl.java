package com.mu9983.service.impl;

import com.mu9983.entity.AuthConstant;
import com.mu9983.entity.LoginInfo;
import com.mu9983.entity.User;
import com.mu9983.mapper.UserMapper;
import com.mu9983.service.UserService;
import com.mu9983.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 登录接口
     * @param user
     * @return
     */
    @Override
    public LoginInfo login(User user) {
        // 匹配登录信息
        User u = userMapper.selectByUsernameAndPassword(user);
        if (!u.getPassword().equals(user.getPassword())) {
            return null;
        }
        // 生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", u.getId());
        claims.put("username", u.getUsername());
        String token = JwtUtils.generateToken(claims);
        // 将token存入redis
        String redisKey = AuthConstant.LOGIN_TOKEN_PREFIX + token;
        redisTemplate.opsForValue().set(redisKey,
                String.valueOf(user.getId()),
                AuthConstant.TOKEN_EXPIRE_MINUTES,
                TimeUnit.MINUTES
                );
        // 封装返回信息
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setToken(token);
        loginInfo.setUsername(u.getUsername());
        loginInfo.setId(u.getId());
        return loginInfo;
    }

    /**
     * 登出接口
     * @param token
     */
    @Override
    public void logout(String token) {
        String  redisKey = AuthConstant.LOGIN_TOKEN_PREFIX + token;
        redisTemplate.delete(redisKey);
    }


}
