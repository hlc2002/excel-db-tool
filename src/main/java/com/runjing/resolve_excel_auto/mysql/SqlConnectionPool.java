package com.runjing.resolve_excel_auto.mysql;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/17
 * @modified By:
 * @project: resolve_excel_auto
 */
@Configuration
@ConditionalOnBean(SqlConfiguration.class)
public class SqlConnectionPool {
}
