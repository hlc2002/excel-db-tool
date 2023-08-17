package com.runjing.resolve_excel_auto.basic;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : forestSpringH
 * @description: 列的属性信息
 * @date : Created in 2023/8/17
 * @modified By: 黄林春
 * @project: resolve_excel_auto
 */
@Data
@EqualsAndHashCode
public class ColumnEntity {
    private String columnName;
    private String columnSqlInfo;
    public void clear(){
        setColumnName(null);
        setColumnSqlInfo(null);
    }
}
