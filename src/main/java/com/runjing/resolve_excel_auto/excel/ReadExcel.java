package com.runjing.resolve_excel_auto.excel;

import com.runjing.resolve_excel_auto.basic.ColumnEntity;
import com.runjing.resolve_excel_auto.basic.ValueEntity;
import com.runjing.resolve_excel_auto.excel.service.ReadExcelService;
import com.runjing.resolve_excel_auto.util.ExcelReadStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
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
@Service
public class ReadExcel implements ReadExcelService {
    private static final Integer LIMIT_SCAN_NUM = 500 * 1000;

    /**
     * 根据文件名读取Excel文件获取列信息列表
     *
     * @param file 文件
     * @return List<列实体>
     */
    @Override
    public List<ColumnEntity> getExcelColumnList(MultipartFile file) {
        List<ColumnEntity> list;
        Workbook workbook = getWorkbook(file);
        if (Objects.isNull(workbook)) {
            return new LinkedList<>();
        }
        list = getExcelColumnList(workbook);
        return list;
    }

    /**
     * 获取行数与对应行得值SQL实体列表
     *
     * @param file             工作薄文件
     * @param columnEntityList 列信息对象
     * @return map<行号 ， 行内每一个单元格得值SQL实体列表>
     */
    @Override
    public Map<Integer, List<ValueEntity>> getExcelRowDataMap(MultipartFile file, List<ColumnEntity> columnEntityList) {
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
                    valueEntity.setValueOfString(ExcelReadStringUtil.getValueSqlString(row.getCell(j)));
                    list.add(valueEntity);
                }
                map.put(i, list);
            }
        }else{
            throw new RuntimeException("扫描的Excel文件数据量超过限定值，请检查核定容量");
        }
        return map;
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
            throw new RuntimeException("DataSheet is error: com.runjing.resolve_excel_auto.excel.ReadExcel.getExcelColumnList()");
        }
        List<ColumnEntity> columnEntityList = new LinkedList<>();
        for (int i = 0; i < topRow.getPhysicalNumberOfCells(); i++) {
            ColumnEntity columnEntity = new ColumnEntity();
            Cell nameCell = topRow.getCell(i);
            Cell typeRowCell = typeRow.getCell(i);
            columnEntity.setColumnName(ExcelReadStringUtil.transferPinYin(nameCell.getStringCellValue()));
            columnEntity.setColumnSqlInfo(ExcelReadStringUtil.switchCellDataSqlInfo(typeRowCell));
            columnEntityList.add(columnEntity);
        }
        return columnEntityList;
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


}