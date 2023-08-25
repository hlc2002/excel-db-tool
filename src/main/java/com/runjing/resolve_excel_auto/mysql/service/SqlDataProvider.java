package com.runjing.resolve_excel_auto.mysql.service;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/25
 * @modified By:
 * @project: resolve_excel_auto
 */
public interface SqlDataProvider {
    /**
     * 执行非查询SQL
     *
     * @param sql
     */
    void executeSql(String sql);

    /**
     * 执行查询判断某些存在SQL
     *
     * @param sql
     * @return 是否存在的对象 为空则不存在
     */
    Object executeSqlAndGetReturn(String sql);

    /**
     * 执行查询SQL
     *
     * @param sql
     * @return 结果的Json字符串
     */
    String executeQuerySql(String sql);
}
