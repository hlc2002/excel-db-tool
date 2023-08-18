package com.runjing.resolve_excel_auto.mysql;

import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/17
 * @modified By:
 * @project: resolve_excel_auto
 */
@Service
public class SqlDataService {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public void executeSql(String sql){
        jdbcTemplate.execute(sql);
    }
}
