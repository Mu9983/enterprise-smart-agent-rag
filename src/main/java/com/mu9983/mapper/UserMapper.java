package com.mu9983.mapper;

import com.mu9983.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectByUsernameAndPassword(User user);
}
