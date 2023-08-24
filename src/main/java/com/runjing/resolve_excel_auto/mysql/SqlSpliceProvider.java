package com.runjing.resolve_excel_auto.mysql;

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
    StringBuffer spliceCreateTableSql(List<ColumnEntity> columnEntityList, String tableName);
    String dropTableSql(String tableName);
    String existsTableSql(String tableName);
    List<String> spliceInsertValueSql(Map<Integer, List<ValueEntity>> map, String tableName);
}
