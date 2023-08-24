package com.runjing.resolve_excel_auto.application.service;

import com.runjing.resolve_excel_auto.application.service.instrcution.ExcelEntityService;
import com.runjing.resolve_excel_auto.basic.ColumnEntity;
import com.runjing.resolve_excel_auto.basic.ValueEntity;
import com.runjing.resolve_excel_auto.excel.ReadExcel;
import com.runjing.resolve_excel_auto.mysql.SqlDataService;
import com.runjing.resolve_excel_auto.mysql.SqlSpliceProvider;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
@Slf4j
@ConditionalOnBean({SqlDataService.class,SqlSpliceProvider.class})
public class ExcelEntityServiceImpl implements ExcelEntityService {

    @Resource
    private SqlDataService service;

    @Resource
    private SqlSpliceProvider spliceProvider;

    @Override
    public void createTable(MultipartFile file) {
        String dropTableSql = spliceProvider.dropTableSql(file.getName());
        log.info("删表SQL：{}",dropTableSql);
        service.executeSql(dropTableSql);
        List<ColumnEntity> excelColumnList = ReadExcel.getExcelColumnList(file);
        StringBuffer stringBuffer = spliceProvider.spliceCreateTableSql(excelColumnList, file.getName());
        log.warn("建表SQL：{}", stringBuffer);
        service.executeSql(stringBuffer.toString());
    }

    @Override
    public void insertEntity(MultipartFile file) {
        String existsTableSql = spliceProvider.existsTableSql(file.getName());
        Object aReturn = service.executeSqlAndGetReturn(existsTableSql);
        if (Objects.nonNull(aReturn)) {
            List<ColumnEntity> excelColumnList = ReadExcel.getExcelColumnList(file);
            Map<Integer, List<ValueEntity>> excelRowDataMap = ReadExcel.getExcelRowDataMap(file, excelColumnList);
            List<String> stringBuffer1 = spliceProvider.spliceInsertValueSql(excelRowDataMap, file.getName());
            stringBuffer1.forEach(s -> service.executeSql(s));
        }else{
            log.warn("不存在数据表：{}",file.getName());
        }
    }

    @Override
    public void dropTable(String fileName) {
        String dropTableSql = spliceProvider.dropTableSql(fileName);
        log.info("删表SQL：{}",dropTableSql);
        service.executeSql(dropTableSql);
    }
}