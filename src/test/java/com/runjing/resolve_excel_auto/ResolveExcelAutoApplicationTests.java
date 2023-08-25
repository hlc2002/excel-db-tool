package com.runjing.resolve_excel_auto;

import com.runjing.resolve_excel_auto.mysql.service.SqlDataProvider;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ResolveExcelAutoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private SqlDataProvider sqlDataProvider;
    @Test
    public void testRollback(){
        String sql = "select * from test.aaa";
        sqlDataProvider.executeSql(sql);
    }

    @Test
    public void testQueryJson(){
        String sql = "SELECT * FROM test.runjing_sku LIMIT 10";
        System.out.println(sqlDataProvider.executeQuerySql(sql));
    }

}
