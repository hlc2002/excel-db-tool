package com.runjing.resolve_excel_auto.mysql;

import com.runjing.resolve_excel_auto.basic.ColumnEntity;
import com.runjing.resolve_excel_auto.basic.ValueEntity;
import com.runjing.resolve_excel_auto.util.SqlSpliceStringUtil;
import org.springframework.stereotype.Service;

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
@Service
public class SqlSplicer implements SqlSpliceProvider{

    /**
     * 拼接建表SQL
     *
     * @param columnEntityList 列信息实体列表
     * @param tableName        表格名称
     * @return SQL
     */
    @Override
    public StringBuffer spliceCreateTableSql(List<ColumnEntity> columnEntityList, String tableName) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CREATE TABLE ").append(SqlSpliceStringUtil.quotesHandle(SqlSpliceStringUtil.transferPinYin(tableName))).append(" ( `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自动主键',");
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
    @Override
    public  String dropTableSql(String tableName) {
        return "DROP TABLE IF EXISTS " + SqlSpliceStringUtil.quotesHandle(SqlSpliceStringUtil.transferPinYin(tableName));
    }

    /**
     * 查询数据表是否存在
     * @param tableName 表名称
     * @return SQL
     */
    @Override
    public  String existsTableSql(String tableName) {
        return "SELECT * FROM information_schema.TABLES WHERE TABLE_NAME = " + SqlSpliceStringUtil.quotesHandle(SqlSpliceStringUtil.transferPinYin(tableName));
    }

    /**
     * 拼接插值SQL(单插入SQL集合)
     *
     * @param map       值实体列表Map
     * @param tableName 表名
     * @return 插值SQL
     */
    @Override
    public  List<String> spliceInsertValueSql(Map<Integer, List<ValueEntity>> map, String tableName) {
        List<String> sqlList = new LinkedList<>();
        map.values().forEach(valueEntityList -> sqlList.add(scanValueListToSql(tableName, valueEntityList)));
        return sqlList;
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
        stringBuffer.append("INSERT INTO ").append(SqlSpliceStringUtil.quotesHandle(SqlSpliceStringUtil.transferPinYin(tableName)));
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

    /**
     * 将列信息实体列表转SQL
     *
     * @param columnEntityList 列信息实体列表
     * @return 处理完成得列属性SQL串
     */
    private static String scanColumnListToSql(List<ColumnEntity> columnEntityList) {
        StringBuilder fieldSql = new StringBuilder();
        for (ColumnEntity element : columnEntityList) {
            fieldSql.append(SqlSpliceStringUtil.quotesHandle(element.getColumnName())).append(element.getColumnSqlInfo());
        }
        return fieldSql.toString();
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
        stringBuffer.append("INSERT INTO ").append(SqlSpliceStringUtil.quotesHandle(SqlSpliceStringUtil.transferPinYin(tableName))).append(" ( `id`,");
        columnEntityList.forEach(columnEntity -> {
            stringBuffer.append(SqlSpliceStringUtil.quotesHandle(SqlSpliceStringUtil.transferPinYin(columnEntity.getColumnName())));
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


}