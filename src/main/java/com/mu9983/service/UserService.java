package com.mu9983.service;

import com.mu9983.entity.LoginInfo;
import com.mu9983.entity.User;

import java.util.Map;

public interface UserService {

    LoginInfo login(User user);

    void logout(Map<String, String> token);

    User currentUser();

    LoginInfo refreshToken(Map<String, String> token);
}
