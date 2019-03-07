package com.tanghan.jtams.service;

import com.tanghan.jtams.dao.mysql.UserMapper;
import com.tanghan.jtams.dao.sqlserver.AnimalMapper;
import com.tanghan.jtams.pojo.Animal;
import com.tanghan.jtams.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(transactionManager = "xatx")
public class TestService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private AnimalMapper animalMapper;

    public void testAdd() throws RuntimeException {
        User user = new User();
        user.setName("小八");
        userMapper.addUser(user);

        Animal animal = new Animal();
        animal.setId(8L);
        animal.setName("老鼠");
        animalMapper.addAnimal(animal);

        throw new RuntimeException();
    }
}
