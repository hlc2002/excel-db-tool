package com.runjing.resolve_excel_auto.excel;

import com.runjing.resolve_excel_auto.basic.ColumnEntity;
import com.runjing.resolve_excel_auto.basic.ValueEntity;
import com.runjing.resolve_excel_auto.util.LanguageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author : forestSpringH
 * @description: excel解析方法
 * @date : Created in 2023/8/17
 * @modified By: 黄林春
 * @project: resolve_excel_auto
 */
@Slf4j
public class ReadExcel {
    private static final Integer LIMIT_SCAN_NUM = 500 * 1000;

    /**
     * 根据文件名读取Excel文件获取列信息列表
     *
     * @param file 文件
     * @return List<列实体>
     */
    public static List<ColumnEntity> getExcelColumnList(MultipartFile file) {
        List<ColumnEntity> list;
        Workbook workbook = getWorkbook(file);
        if (Objects.isNull(workbook)) {
            return new LinkedList<>();
        }
        list = getExcelColumnList(workbook);
        return list;
    }

    /**
     * 解析返回excel数据表第一行属性信息列表数据
     *
     * @param workbook 数据工作薄
     * @return List<列实体>
     */
    private static List<ColumnEntity> getExcelColumnList(Workbook workbook) {
        /*默认取第一个工作表的第一行数据与第二行数据（第一行用来感知属性名称、第二行用来感知属性类型）*/
        Sheet dataSheet = workbook.getSheetAt(0);
        Row topRow = dataSheet.getRow(0);
        Row typeRow = dataSheet.getRow(1);
        if (topRow.getPhysicalNumberOfCells() != typeRow.getPhysicalNumberOfCells()) {
            log.error("数据表列行与数据行列数不一致！退出解析，请整理数据表格式！");
            throw new RuntimeException("DataSheet is error: com.runjing.resolve_excel_auto.excel.ReadExcel.getExcelColumnList(org.apache.poi.ss.usermodel.Workbook)");
        }
        List<ColumnEntity> columnEntityList = new LinkedList<>();
        for (int i = 0; i < topRow.getPhysicalNumberOfCells(); i++) {
            ColumnEntity columnEntity = new ColumnEntity();
            Cell nameCell = topRow.getCell(i);
            Cell typeRowCell = typeRow.getCell(i);
            columnEntity.setColumnName(transferPinYin(nameCell.getStringCellValue()));
            columnEntity.setColumnSqlInfo(switchCellDataSqlInfo(typeRowCell));
            columnEntityList.add(columnEntity);
        }
        return columnEntityList;
    }

    /**
     * 获取行数与对应行得值SQL实体列表
     *
     * @param file             工作薄文件
     * @param columnEntityList 列信息对象
     * @return map<行号 ， 行内每一个单元格得值SQL实体列表>
     */
    public static Map<Integer, List<ValueEntity>> getExcelRowDataMap(MultipartFile file, List<ColumnEntity> columnEntityList) {
        Workbook workbook = getWorkbook(file);
        if (Objects.isNull(workbook)) {
            return new HashMap<>();
        }
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        Map<Integer, List<ValueEntity>> map = new HashMap<>();
        if (lastRowNum <= LIMIT_SCAN_NUM) {
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                List<ValueEntity> list = new LinkedList<>();
                for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                    ValueEntity valueEntity = new ValueEntity();
                    valueEntity.setColumnName(columnEntityList.get(j).getColumnName());
                    valueEntity.setValueOfString(getValueSqlString(row.getCell(j)));
                    list.add(valueEntity);
                }
                map.put(i, list);
            }
        }
        return map;
    }

    /**
     * 判断是否Excel格式是否为2003
     */
    private static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /*获取构建表格数据属性SQL*/
    private static String switchCellDataSqlInfo(Cell dataCell) {
        return switch (dataCell.getCellType()) {
            case NUMERIC -> " double default 0.00 ";
            case STRING -> " varchar(100) default null";
            case FORMULA -> " varchar default null";
            case BOOLEAN -> " tinyint(1) default 0";
            default -> " varchar(64) default null";
        } + ",";
    }

    /*获取单元格值SQL*/
    private static String getValueSqlString(Cell dataCell) {
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
    private static String quotesHandle(String fieldValue) {
        return "'" + fieldValue + "'";
    }

    /**
     * 布尔类型转换
     *
     * @param arg1 入参
     * @return 转换值
     */
    private static String transferBool(Boolean arg1) {
        return arg1 ? "0" : "1";
    }

    /**
     * 文件转换工作簿对象
     *
     * @param file excel文件
     * @return 工作簿对象
     */
    private static Workbook getWorkbook(MultipartFile file) {
        InputStream is = null;
        try {
            is = file.getInputStream();
            Workbook workbook;
            workbook = WorkbookFactory.create(is);
            return workbook;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
}