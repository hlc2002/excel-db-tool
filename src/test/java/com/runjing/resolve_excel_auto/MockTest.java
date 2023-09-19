package com.runjing.resolve_excel_auto;

import com.runjing.resolve_excel_auto.mysql.SqlDataService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/9/6
 * @modified By:
 * @project: resolve_excel_auto
 */
@RunWith(PowerMockRunner.class)
@SpringBootTest
public class MockTest {
    @Mock
    private SqlDataService service;

    @Test
    public void mock(){
        Object mock = PowerMockito.mock(Object.class);
        PowerMockito.when(service.executeSqlAndGetReturn("")).thenReturn(mock);
        Assert.assertNull("", mock);
    }
}
