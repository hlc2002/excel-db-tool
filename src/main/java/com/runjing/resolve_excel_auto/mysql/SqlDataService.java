package com.runjing.resolve_excel_auto.mysql;


import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/17
 * @modified By:
 * @project: resolve_excel_auto
 */
@Service
@ConditionalOnBean(SqlConnectionPool.class)
public class SqlDataService {

}
