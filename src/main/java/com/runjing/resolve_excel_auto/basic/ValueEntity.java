package com.runjing.resolve_excel_auto.basic;

import lombok.Data;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/17
 * @modified By:
 * @project: resolve_excel_auto
 */
@Data
public class ValueEntity{
    private String columnName;
    /*数据拼接到SQL上也是字符串类型*/
    private String valueOfString;
    public void clear(){
        setColumnName(null);
        setValueOfString(null);
    }
}
