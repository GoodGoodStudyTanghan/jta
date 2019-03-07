package com.tanghan.jtams.config;

import com.microsoft.sqlserver.jdbc.SQLServerXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.tanghan.jtams.dao.sqlserver", sqlSessionTemplateRef  = "sqlSessionTemplateSecondary")
public class SQLServerDataSourceConfig {
	@Autowired
    private Environment env;
    @Autowired
    @Qualifier("sqlServerDS")
    private DataSource sqlServerDS;

    @Bean(name = "sqlServerDS")
    public DataSource dataSource(@Qualifier("sqlServerDataConfig") SQLServerDataConfig sqlServerDataConfig) throws Exception{
        SQLServerXADataSource sqlServerXaDataSource = new SQLServerXADataSource();
        sqlServerXaDataSource.setURL(sqlServerDataConfig.getUrl());
        sqlServerXaDataSource.setPassword(sqlServerDataConfig.getPassword());
        sqlServerXaDataSource.setUser(sqlServerDataConfig.getUsername());

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(sqlServerXaDataSource);
        xaDataSource.setXaDataSourceClassName("com.microsoft.sqlserver.jdbc.SQLServerXADataSource");
        xaDataSource.setUniqueResourceName("sqlserverDataSource");
        xaDataSource.setMinPoolSize(sqlServerDataConfig.getMinPoolSize());
        xaDataSource.setMaxPoolSize(sqlServerDataConfig.getMaxPoolSize());
        xaDataSource.setMaxLifetime(sqlServerDataConfig.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(sqlServerDataConfig.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(sqlServerDataConfig.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(sqlServerDataConfig.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(sqlServerDataConfig.getMaxIdleTime());
        xaDataSource.setTestQuery(sqlServerDataConfig.getTestQuery());

        return xaDataSource;
    }

    @Bean(name="sqlSessionFactorySecondary")
    public SqlSessionFactory sqlSessionFactorySecondary() throws Exception{
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(sqlServerDS);
        fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));//指定基包
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.sqlserver")));//指定xml文件位置
        return fb.getObject();
    }
    
    @Bean(name = "sqlSessionTemplateSecondary")
    public SqlSessionTemplate sqlSessionTemplateSecondary(@Qualifier("sqlSessionFactorySecondary") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}