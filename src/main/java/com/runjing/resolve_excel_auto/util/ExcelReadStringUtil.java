package com.runjing.resolve_excel_auto.util;

import org.apache.poi.ss.usermodel.Cell;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/24
 * @modified By:
 * @project: resolve_excel_auto
 */
public class ExcelReadStringUtil {

    /*获取构建表格数据属性SQL*/
    public static String switchCellDataSqlInfo(Cell dataCell) {
        return switch (dataCell.getCellType()) {
            case NUMERIC -> " double default 0.00 ";
            case STRING -> " varchar(100) default null";
            case FORMULA -> " varchar default null";
            case BOOLEAN -> " tinyint(1) default 0";
            default -> " varchar(64) default null";
        } + ",";
    }

    /*获取单元格值SQL*/
    public static String getValueSqlString(Cell dataCell) {
        return switch (dataCell.getCellType()) {
            case NUMERIC -> String.valueOf(dataCell.getNumericCellValue());
            case STRING -> quotesHandle(dataCell.getStringCellValue());
            case FORMULA -> quotesHandle(dataCell.getCellFormula());
            case BOOLEAN -> transferBool(dataCell.getBooleanCellValue());
            default -> "null";
        };
    }

    /**
     * 单引号包裹字段
     *
     * @param fieldValue 字段值
     * @return 包裹后字段串
     */
    public static String quotesHandle(String fieldValue) {
        return "'" + fieldValue + "'";
    }

    /**
     * 布尔类型转换
     *
     * @param arg1 入参
     * @return 转换值
     */
    public static String transferBool(Boolean arg1) {
        return arg1 ? "0" : "1";
    }
    /**
     * 将汉字串转成拼音串
     *
     * @param columnChineseName 汉字字段名
     * @return 字段拼音
     */
    public static String transferPinYin(String columnChineseName) {
        /*转换中文为简体拼音*/
        return LanguageUtil.convertChineseLan2PinYinAbbreviation(columnChineseName, LanguageUtil.CHINESE_CHAR_REG_SIMPLIFIED);
    }
}
