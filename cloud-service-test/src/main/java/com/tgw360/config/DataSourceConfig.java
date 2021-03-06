package com.tgw360.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by 易弘博 on 2018/1/31 15:05
 */
@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = DataSourceConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class DataSourceConfig {

    // 精确到 mapper 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.tgw360.mapper";
    static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";

    @Value("${mapper.datasource.url}")
    private String url;

    @Value("${mapper.datasource.username}")
    private String user;

    @Value("${mapper.datasource.password}")
    private String password;

    @Value("${mapper.datasource.driverClassName}")
    private String driverClass;

    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "masterTransactionManager")
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
