package com.runjing.resolve_excel_auto.mysql.config;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/24
 * @modified By:
 * @project: resolve_excel_auto
 */
@Configuration
@ConditionalOnBean({SqlDataSourceConfiguration.class})
@Slf4j
public class JdbcTransactionManagerConfiguration {

    @Resource
    private DriverManagerDataSource driverManagerDataSource;
    @Bean("JdbcTransactionManager")
    @Scope("singleton")
    public JdbcTransactionManager getJdbcTransactionManager(){
        JdbcTransactionManager jdbcTransactionManager = new JdbcTransactionManager();
        log.info("开始配置JDBC事务管理者");
        jdbcTransactionManager.setDataSource(driverManagerDataSource);
        jdbcTransactionManager.setRollbackOnCommitFailure(true);
        jdbcTransactionManager.setFailEarlyOnGlobalRollbackOnly(true);
        jdbcTransactionManager.setGlobalRollbackOnParticipationFailure(true);
        return jdbcTransactionManager;
    }

}
