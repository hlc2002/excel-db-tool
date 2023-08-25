package com.runjing.resolve_excel_auto;

import com.runjing.resolve_excel_auto.mysql.SqlDataService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ResolveExcelAutoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private SqlDataService service;
    @Test
    public void testRollback(){
        String sql = "select * from test.aaa";
        service.executeSql(sql);
    }

}
