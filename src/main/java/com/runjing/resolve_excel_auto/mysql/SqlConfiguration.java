package com.runjing.resolve_excel_auto.mysql;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/17
 * @modified By:
 * @project: resolve_excel_auto
 */
@Component("SqlConfiguration")
@ConfigurationProperties(prefix = "jdbc-config")
@Data
public class SqlConfiguration {
    private String driver;
    private String url;
    private String userName;
    private String password;
}
