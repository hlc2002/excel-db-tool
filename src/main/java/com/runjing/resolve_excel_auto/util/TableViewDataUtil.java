package com.runjing.resolve_excel_auto.util;

import com.runjing.resolve_excel_auto.basic.ColumnEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/9/20
 * @modified By:
 * @project: resolve_excel_auto
 */
public class TableViewDataUtil {
    private static final Map<String,List<ColumnEntity>> COLUMN_ENTITY_MAP = new HashMap<>();
    private static final List<String> TABLE_NAME_LIST = new ArrayList<>();
    public static List<ColumnEntity> fillColumnEntity(String tableName,List<ColumnEntity> columnEntityList){
        if (TABLE_NAME_LIST.contains(tableName)){
            if (COLUMN_ENTITY_MAP.containsKey(tableName) && COLUMN_ENTITY_MAP.containsValue(columnEntityList)){
                return COLUMN_ENTITY_MAP.get(tableName);
            }
            COLUMN_ENTITY_MAP.put(tableName,columnEntityList);
        }
        TABLE_NAME_LIST.add(tableName);
        COLUMN_ENTITY_MAP.put(tableName,columnEntityList);
        return columnEntityList;
    }
}
