package com.tanghan.jtams.dao.mysql;

import com.tanghan.jtams.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void addUser(User user);
}
