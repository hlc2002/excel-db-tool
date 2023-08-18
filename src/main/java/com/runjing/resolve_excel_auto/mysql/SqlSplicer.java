package com.runjing.resolve_excel_auto.mysql;

import com.runjing.resolve_excel_auto.basic.ColumnEntity;
import com.runjing.resolve_excel_auto.basic.ValueEntity;
import com.runjing.resolve_excel_auto.util.LanguageUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/17
 * @modified By:
 * @project: resolve_excel_auto
 */
public class SqlSplicer {

    /**
     * 拼接建表SQL
     *
     * @param columnEntityList 列信息实体列表
     * @param tableName        表格名称
     * @return SQL
     */
    public static StringBuffer spliceCreateTableSql(List<ColumnEntity> columnEntityList, String tableName) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CREATE TABLE ").append(quotesHandle(transferPinYin(tableName))).append(" ( `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自动主键',");
        /*扫描属性列表，填充建表SQL*/
        stringBuffer.append(scanColumnListToSql(columnEntityList));
        stringBuffer.append(" PRIMARY KEY (`id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4; ");
        return stringBuffer;
    }

    /**
     * 删除数据表，防止表重复
     *
     * @param tableName 表名称
     * @return SQL
     */
    public static String dropTableSql(String tableName) {
        return "DROP TABLE IF EXISTS " + quotesHandle(transferPinYin(tableName));
    }

    /**
     * 查询数据表是否存在
     * @param tableName 表名称
     * @return SQL
     */
    public static String existsTableSql(String tableName) {
        return "SELECT * FROM information_schema.TABLES WHERE TABLE_NAME = " + quotesHandle(transferPinYin(tableName));
    }

    /**
     * 反引号处理
     *
     * @param fieldName 字段名称
     * @return 被反引号包裹得字段名称
     */
    private static String quotesHandle(String fieldName) {
        return "`" + fieldName + "`";
    }

    /**
     * 将列信息实体列表转SQL
     *
     * @param columnEntityList 列信息实体列表
     * @return 处理完成得列属性SQL串
     */
    private static String scanColumnListToSql(List<ColumnEntity> columnEntityList) {
        StringBuilder fieldSql = new StringBuilder();
        for (ColumnEntity element : columnEntityList) {
            fieldSql.append(quotesHandle(element.getColumnName())).append(element.getColumnSqlInfo());
        }
        return fieldSql.toString();
    }

    /**
     * 将汉字串转成拼音串
     *
     * @param columnChineseName 汉字字段名
     * @return 字段拼音
     */
    private static String transferPinYin(String columnChineseName) {
        /*转换中文为简体拼音*/
        return LanguageUtils.convertChineseLan2PinYinAbbreviation(columnChineseName, LanguageUtils.CHINESE_CHAR_REG_SIMPLIFIED);
    }

    /**
     * 拼接插值SQL(单插入SQL集合)
     *
     * @param map       值实体列表Map
     * @param tableName 表名
     * @return 插值SQL
     */
    public static List<String> spliceInsertValueSql(Map<Integer, List<ValueEntity>> map, String tableName) {
        List<String> sqlList = new LinkedList<>();
        map.values().forEach(valueEntityList -> sqlList.add(scanValueListToSql(tableName, valueEntityList)));
        return sqlList;
    }

    /**
     * 拼接全插入SQL
     *
     * @param map              值实体列表Map
     * @param columnEntityList 列信息实体列表
     * @param tableName        表名
     * @return 全量插入SQL
     */
    public static String spliceBatchInsertValueSql(Map<Integer, List<ValueEntity>> map, List<ColumnEntity> columnEntityList, String tableName) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("INSERT INTO ").append(quotesHandle(transferPinYin(tableName))).append(" ( `id`,");
        columnEntityList.forEach(columnEntity -> {
            stringBuffer.append(quotesHandle(transferPinYin(columnEntity.getColumnName())));
            if (!Objects.equals(columnEntity, columnEntityList.get(columnEntityList.size() - 1))) {
                stringBuffer.append(",");
            } else {
                stringBuffer.append(")");
            }
        });
        map.values().forEach(valueEntityList -> {
            stringBuffer.append(" SELECT ");
            valueEntityList.forEach(valueEntity -> {
                if (Objects.equals(valueEntity, valueEntityList.get(valueEntityList.size() - 1))) {
                    stringBuffer.append(valueEntity.getValueOfString()).append(" ");
                } else {
                    stringBuffer.append(valueEntity.getValueOfString()).append(",");
                }
            });
            if (Objects.equals(valueEntityList, map.values().stream().toList().get(map.size() - 1))) {
                stringBuffer.append(";");
            } else {
                stringBuffer.append(" UNION ");
            }
        });
        return stringBuffer.toString();
    }

    /**
     * 拼接单一数据行值SQL
     *
     * @param tableName       表名
     * @param valueEntityList 一行数据值列表
     * @return SQL
     */
    private static String scanValueListToSql(String tableName, List<ValueEntity> valueEntityList) {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("INSERT INTO ").append(quotesHandle(transferPinYin(tableName)));
        stringBuffer.append(" VALUES( null,");
        for (ValueEntity element : valueEntityList) {
            if ((valueEntityList.indexOf(element) + 1) != valueEntityList.toArray().length) {
                stringBuffer.append(element.getValueOfString()).append(",");
            } else {
                stringBuffer.append(element.getValueOfString());
            }
        }
        stringBuffer.append(");");
        return stringBuffer.toString();
    }

    /*测试SQL拼接情况*/
    public static void main(String[] args) {
        List<ColumnEntity> columnEntityList = new LinkedList<>();
        ColumnEntity column1 = new ColumnEntity();
        column1.setColumnName("品类名称");
        column1.setColumnSqlInfo(" varchar(16) default null ,");
        columnEntityList.add(column1);
        String tableName = "数据表测试";
        StringBuffer stringBuffer = spliceCreateTableSql(columnEntityList, tableName);
        System.out.println(stringBuffer);
        List<ValueEntity> valueEntityList = new LinkedList<>();
        ValueEntity value1 = new ValueEntity();
        value1.setColumnName("品类名称");
        value1.setValueOfString("啤酒");
        valueEntityList.add(value1);
        String s = scanValueListToSql(tableName, valueEntityList);
        System.out.println(s);
    }
}