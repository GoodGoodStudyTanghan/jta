package com.tanghan.jtams;

import com.tanghan.jtams.service.AnimalService;
import com.tanghan.jtams.service.TestService;
import com.tanghan.jtams.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JtamsApplicationTests {

    @Autowired
    private TestService testService;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private UserService userService;
    @Autowired
    private Environment env;

    @Test
    public void testAdd() {
        try {
            testService.testAdd();
        } catch (Exception e) {
            System.out.println("出错了" + e.getMessage());
        }
    }
}
