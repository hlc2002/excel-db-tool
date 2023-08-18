package com.runjing.resolve_excel_auto.application.api;

import com.runjing.resolve_excel_auto.basic.ColumnEntity;
import com.runjing.resolve_excel_auto.basic.ValueEntity;
import com.runjing.resolve_excel_auto.excel.ReadExcel;
import com.runjing.resolve_excel_auto.mysql.SqlDataService;
import com.runjing.resolve_excel_auto.mysql.SqlSplicer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/17
 * @modified By:
 * @project: resolve_excel_auto
 */
@RestController
@RequestMapping("/excel")
@CrossOrigin
@Slf4j
public class ExcelResolveController {
    @Resource
    private SqlDataService service;

    @RequestMapping("/upload")
    @ResponseBody
    public void uploadExcel(@RequestParam("fileName") MultipartFile file) {
        String dropTableSql = SqlSplicer.dropTableSql(file.getName());
        log.info("删表SQL：{}",dropTableSql);
        service.executeSql(dropTableSql);
        List<ColumnEntity> excelColumnList = ReadExcel.getExcelColumnList(file);
        StringBuffer stringBuffer = SqlSplicer.spliceCreateTableSql(excelColumnList, file.getName());
        log.warn("建表SQL：{}", stringBuffer);
        service.executeSql(stringBuffer.toString());
        Map<Integer, List<ValueEntity>> excelRowDataMap = ReadExcel.getExcelRowDataMap(file, excelColumnList);
        List<String> stringBuffer1 = SqlSplicer.spliceInsertValueSql(excelRowDataMap, file.getName());
        log.warn("插值SQL：{}", stringBuffer1);
        stringBuffer1.forEach(s -> service.executeSql(s));
//        String batchInsertValueSql = SqlSplicer.spliceBatchInsertValueSql(excelRowDataMap, excelColumnList, file.getName());
//        log.warn("全量插值SQL：{}", batchInsertValueSql);
//        service.executeSql(batchInsertValueSql);
    }
}
