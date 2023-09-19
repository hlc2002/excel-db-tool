package com.runjing.resolve_excel_auto;

import com.runjing.resolve_excel_auto.mysql.SqlDataService;
import com.runjing.resolve_excel_auto.mysql.service.SqlDataProvider;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

    @MockBean
    SqlDataService service;
    @Test
    public void mock(){
        Mockito.when(service.executeQuerySql("SELECT id FROM test.runjing_sku WHERE id = '11111111' LIMIT 1")).thenReturn(null);
        String s = service.executeQuerySql("SELECT id FROM test.runjing_sku WHERE id = '11111111' LIMIT 1");
        Mockito.verify(service).executeQuerySql("SELECT id FROM test.runjing_sku WHERE id = '11111111' LIMIT 1");
        Assertions.assertNull(s);
    }

}
