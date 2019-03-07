package com.tanghan.jtams.config;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 以MySQL作为主数据源
 */
@Configuration
@MapperScan(basePackages = "com.tanghan.jtams.dao.mysql", sqlSessionTemplateRef  = "sqlSessionTemplatePrimary")
public class MySQLDataSourceConfig {
	@Autowired
    private Environment env;
    @Autowired
    @Qualifier("mysqlDS")
    private DataSource mysqlDS;

    @Bean(name = "mysqlDS")
    @Primary
    public DataSource primaryDataSource(@Qualifier("mysqlDataConfig") MySQLDataConfig dataConfig) throws Exception{
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(dataConfig.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(dataConfig.getPassword());
        mysqlXaDataSource.setUser(dataConfig.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("mysqlDataSource");
        xaDataSource.setMinPoolSize(dataConfig.getMinPoolSize());
        xaDataSource.setMaxPoolSize(dataConfig.getMaxPoolSize());
        xaDataSource.setMaxLifetime(dataConfig.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(dataConfig.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(dataConfig.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(dataConfig.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(dataConfig.getMaxIdleTime());
        xaDataSource.setTestQuery(dataConfig.getTestQuery());

        return xaDataSource;
    }
    
    @Bean(name="sqlSessionFactoryPrimary")
    @Primary
    public SqlSessionFactory sqlSessionFactoryPrimary() throws Exception{
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(mysqlDS);
        fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));//指定实体类存放位置
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mysql")));//指定xml文件位置
        return fb.getObject();
    }
    
    @Bean(name = "sqlSessionTemplatePrimary")
    @Primary
    public SqlSessionTemplate sqlSessionTemplatePrimary(@Qualifier("sqlSessionFactoryPrimary") SqlSessionFactory sqlSessionFactory) throws Exception {
    	SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
    	return sqlSessionTemplate;
    }
}