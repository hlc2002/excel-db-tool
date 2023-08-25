package com.runjing.resolve_excel_auto.mysql;

import com.runjing.resolve_excel_auto.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/17
 * @modified By:
 * @project: resolve_excel_auto
 */
@Service
@Slf4j
public class SqlDataService {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Qualifier("ReadCommittedTransactionTemplate")
    @Resource
    private TransactionTemplate transactionTemplate;

    /**
     * 执行非查询SQL
     *
     * @param sql
     */
    public void executeSql(String sql) {
        log.info("执行非查询操作SQL,开启事务执行：{}", sql);
        transactionTemplate.executeWithoutResult(status -> {
            try {
                jdbcTemplate.execute(sql);
            } catch (Exception e) {
                log.error("事务异常，开启回滚：{}", e.getMessage());
                status.setRollbackOnly();
            }
        });
    }

    /**
     * 执行查询判断某些存在SQL
     *
     * @param sql
     * @return 是否存在的对象 为空则不存在
     */
    public Object executeSqlAndGetReturn(String sql) {
        log.info("执行查询SQL：{}", sql);
        return jdbcTemplate.queryForObject(sql, Object.class);
    }

    /**
     * 执行查询SQL
     *
     * @param sql
     * @return 结果的Json字符串
     */
    public String executeQuerySql(String sql) {
        log.info("执行查询SQL：{}", sql);
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        if (CollectionUtils.isEmpty(mapList)) {
            return "";
        }
        List<String> result = new LinkedList<>();
        mapList.forEach(map -> result.add(JsonUtil.mapToJsonString(map)));
        return result.toString();
    }
}