package com.mu9983.mapper;

import com.mu9983.entity.User;
import com.mu9983.entity.VerifyUserPassword;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectByUsernameAndPassword(User user);

    User selectById(Integer id);

    void updateUser(User user);

    User selectVerifyUserPassword(VerifyUserPassword verifyUserPassword);
}
