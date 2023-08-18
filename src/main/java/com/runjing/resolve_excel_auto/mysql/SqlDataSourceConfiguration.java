package com.runjing.resolve_excel_auto.mysql;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/18
 * @modified By:
 * @project: resolve_excel_auto
 */
@Configuration
@ConditionalOnBean(SqlConfiguration.class)
@Slf4j
public class SqlDataSourceConfiguration {
    @Resource
    private SqlConfiguration sqlConfiguration;

    @Bean("DriverManagerDataSource")
    @Scope(value = "singleton")
    public DriverManagerDataSource getDataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(sqlConfiguration.getDriver());
        driverManagerDataSource.setUrl(sqlConfiguration.getUrl());
        driverManagerDataSource.setUsername(sqlConfiguration.getUserName());
        driverManagerDataSource.setPassword(sqlConfiguration.getPassword());
        log.warn("开启自定义配置JDBC数据源：{}",sqlConfiguration.toString());
        return driverManagerDataSource;
    }
}
