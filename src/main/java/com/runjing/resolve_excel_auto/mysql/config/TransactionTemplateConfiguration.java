package com.runjing.resolve_excel_auto.mysql.config;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
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
@Slf4j
public class TransactionTemplateConfiguration {
    @Resource
    private JdbcTransactionManager jdbcTransactionManager;

    @Bean("ReadCommittedTransactionTemplate")
    @Scope("singleton")
    @ConditionalOnBean({JdbcTransactionManager.class})
    @Lazy
    public TransactionTemplate getReadCommittedTransactionTemplate(){
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        log.info("生成事务模板，注入事务管理器，设置事务隔离级别为读已提交");
        transactionTemplate.setTransactionManager(jdbcTransactionManager);
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        return transactionTemplate;
    }

    @Bean("ReadUnCommittedTransactionTemplate")
    @Scope("singleton")
    @ConditionalOnBean({JdbcTransactionManager.class})
    @Lazy
    public TransactionTemplate getReadUnCommittedTransactionTemplate(){
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        log.info("生成事务模板，注入事务管理器，设置事务隔离级别为读未提交");
        transactionTemplate.setTransactionManager(jdbcTransactionManager);
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
        return transactionTemplate;
    }

    @Bean("RepeatableReadTransactionTemplate")
    @Scope("singleton")
    @ConditionalOnBean({JdbcTransactionManager.class})
    @Lazy
    public TransactionTemplate getRepeatableReadTransactionTemplate(){
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        log.info("生成事务模板，注入事务管理器，设置事务隔离级别为可重复读");
        transactionTemplate.setTransactionManager(jdbcTransactionManager);
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        return transactionTemplate;
    }

    @Bean("SerializableTransactionTemplate")
    @Scope("singleton")
    @ConditionalOnBean({JdbcTransactionManager.class})
    @Lazy
    public TransactionTemplate getSerializableTransactionTemplate(){
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        log.info("生成事务模板，注入事务管理器，设置事务隔离级别为可串行化");
        transactionTemplate.setTransactionManager(jdbcTransactionManager);
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        return transactionTemplate;
    }

}
