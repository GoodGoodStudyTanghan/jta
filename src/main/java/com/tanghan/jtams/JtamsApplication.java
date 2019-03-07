package com.tanghan.jtams;

import com.tanghan.jtams.config.MySQLDataConfig;
import com.tanghan.jtams.config.SQLServerDataConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = {"com.tanghan.jtams.dao"})//扫描dao接口
@EnableTransactionManagement//启用事务管理器
@EnableConfigurationProperties({MySQLDataConfig.class, SQLServerDataConfig.class})
public class JtamsApplication {
    public static void main(String[] args) {
        SpringApplication.run(JtamsApplication.class, args);
    }

}
