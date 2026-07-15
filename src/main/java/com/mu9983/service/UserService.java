package com.mu9983.service;

import com.mu9983.entity.LoginInfo;
import com.mu9983.entity.User;

public interface UserService {

    LoginInfo login(User user);

    void logout(String token);
}
