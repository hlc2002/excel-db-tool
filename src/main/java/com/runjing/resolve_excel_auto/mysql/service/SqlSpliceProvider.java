package com.runjing.resolve_excel_auto.mysql.service;

import com.runjing.resolve_excel_auto.basic.ColumnEntity;
import com.runjing.resolve_excel_auto.basic.ValueEntity;

import java.util.List;
import java.util.Map;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/24
 * @modified By:
 * @project: resolve_excel_auto
 */
public interface SqlSpliceProvider {
    /**
     * 拼接建表SQL
     *
     * @param columnEntityList excel的列属性列表
     * @param tableName        表名
     * @return SQL
     */
    StringBuffer spliceCreateTableSql(List<ColumnEntity> columnEntityList, String tableName);

    /**
     * 拼接删表SQL
     *
     * @param tableName 表名
     * @return SQL
     */
    String dropTableSql(String tableName);

    /**
     * 拼接判断表存在SQL
     *
     * @param tableName 表名
     * @return SQL
     */
    String existsTableSql(String tableName);

    /**
     * 拼接插值SQL列表循环执行即可
     *
     * @param map       值map<行号，对应行的值LIST>
     * @param tableName 表名
     * @return SQL
     */
    List<String> spliceInsertValueSql(Map<Integer, List<ValueEntity>> map, String tableName);

}
