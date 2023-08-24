package com.runjing.resolve_excel_auto.mysql;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.JdbcTransactionObjectSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Consumer;


/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/17
 * @modified By:
 * @project: resolve_excel_auto
 */
@Service
@Slf4j
public class SqlDataService{
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Qualifier("TransactionTemplate")
    @Resource
    private TransactionTemplate transactionTemplate;

    public void executeSql(String sql){
        log.warn("执行非查询操作SQL,开启事务执行{}",sql);
        transactionTemplate.executeWithoutResult(status -> {
            try{
                jdbcTemplate.execute(sql);
            }catch (Exception e){
                log.warn("事务异常，开启回滚：{}",e.getMessage());
                status.setRollbackOnly();
            }
        });
    }

    public Object executeSqlAndGetReturn(String sql){
        return jdbcTemplate.queryForObject(sql,Object.class);
    }
}