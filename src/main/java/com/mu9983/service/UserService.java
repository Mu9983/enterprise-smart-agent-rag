package com.mu9983.service;

import com.mu9983.entity.LoginInfo;
import com.mu9983.entity.User;
import com.mu9983.entity.VerifyUserPassword;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UserService {

    LoginInfo login(User user);

    void logout(Map<String, String> token);

    User currentUser();

    LoginInfo refreshToken(Map<String, String> token);

    void changeUser(User user, MultipartFile avatar) throws Exception;

    boolean verifyPassword(VerifyUserPassword verifyUserPassword);
}
