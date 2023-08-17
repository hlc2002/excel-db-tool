package com.runjing.resolve_excel_auto.excel;

import com.runjing.resolve_excel_auto.basic.ColumnEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
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

   private final static ColumnEntity COLUMN_ENTITY = new ColumnEntity();

    /**
     * 根据文件名读取Excel文件获取列信息列表
     *
     * @param file 文件
     * @return List<列实体>
     */
    public static List<ColumnEntity> getExcelColumnList(MultipartFile file) {
        List<ColumnEntity> list = new LinkedList<>();
        InputStream is = null;
        try {
            is = (FileInputStream)file.getInputStream();
            Workbook workbook;
            workbook = WorkbookFactory.create(is);
            list = getExcelColumnList(workbook);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
        if (topRow.getPhysicalNumberOfCells() != typeRow.getPhysicalNumberOfCells()){
           log.error("数据表列行与数据行列数不一致！退出解析，请整理数据表格式！");
           throw new RuntimeException("DataSheet is error: com.runjing.resolve_excel_auto.excel.ReadExcel.getExcelColumnList(org.apache.poi.ss.usermodel.Workbook)");
        }
        List<ColumnEntity> columnEntityList = new LinkedList<>();
        for (int i = 0; i < topRow.getPhysicalNumberOfCells(); i++) {
            Cell nameCell = topRow.getCell(i);
            Cell typeRowCell = typeRow.getCell(i);
            COLUMN_ENTITY.setColumnName(nameCell.getStringCellValue());
            COLUMN_ENTITY.setColumnSqlInfo(switchCellDataSqlInfo(typeRowCell));
            columnEntityList.add(COLUMN_ENTITY);
            COLUMN_ENTITY.clear();
        }
        return columnEntityList;
    }

    public Map<Long,List<ColumnEntity>> getExcelRowDataMap(){
        return null;
    }
    /**
     * 判断是否Excel格式是否为2003
     */
    private static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    private static String switchCellDataSqlInfo(Cell dataCell){
       return switch (dataCell.getCellType().getCode()) {
            case 0 -> "int default 0";
            case 2 -> "varchar(32) default null";
            case 3 -> "varchar default null";
            case 4 -> "tinyint(1) default 0";
            default -> "varchar(16) default null";
        };
    }
}
