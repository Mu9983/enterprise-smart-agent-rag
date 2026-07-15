package com.mu9983.service.impl;

import com.mu9983.entity.LoginInfo;
import com.mu9983.entity.User;
import com.mu9983.mapper.UserMapper;
import com.mu9983.service.UserService;
import com.mu9983.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;



    @Override
    public LoginInfo Login(User user) {
        User u = userMapper.selectByUsernameAndPassword(user);
        if (u != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", u.getId());
            claims.put("username", u.getUsername());
            String jwt = JwtUtils.generateToken(claims);
            return new LoginInfo(u.getId(),  u.getUsername(), u.getPassword(), jwt);
        }
        return null;
    }
}
