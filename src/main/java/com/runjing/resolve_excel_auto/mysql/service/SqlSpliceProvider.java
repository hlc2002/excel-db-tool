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
public  interface SqlSpliceProvider {
    /**
     * @param columnEntityList
     * @param tableName
     * @return
     */
    StringBuffer spliceCreateTableSql(List<ColumnEntity> columnEntityList, String tableName);

    /**
     * @param tableName
     * @return
     */
    String dropTableSql(String tableName);

    /**
     * @param tableName
     * @return
     */
    String existsTableSql(String tableName);

    /**
     * @param map
     * @param tableName
     * @return
     */
    List<String> spliceInsertValueSql(Map<Integer, List<ValueEntity>> map, String tableName);
}
