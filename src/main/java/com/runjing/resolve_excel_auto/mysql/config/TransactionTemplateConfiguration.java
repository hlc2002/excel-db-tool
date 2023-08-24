package com.runjing.resolve_excel_auto.mysql.config;

import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/24
 * @modified By:
 * @project: resolve_excel_auto
 */
@Configuration
public class TransactionTemplateConfiguration {
    @Resource
    private JdbcTransactionManager jdbcTransactionManager;
    @Bean("TransactionTemplate")
    @Scope("singleton")
    @ConditionalOnBean({JdbcTransactionManager.class})
    public TransactionTemplate getTransactionTemplate(){
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(jdbcTransactionManager);
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        return transactionTemplate;
    }
}
