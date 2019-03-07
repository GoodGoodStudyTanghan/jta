package com.tanghan.jtams.service;

import com.tanghan.jtams.dao.mysql.UserMapper;
import com.tanghan.jtams.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public void addUser(User user) {
        userMapper.addUser(user);
    }
}
