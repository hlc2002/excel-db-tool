package com.runjing.resolve_excel_auto.excel.service;

import com.runjing.resolve_excel_auto.basic.ColumnEntity;
import com.runjing.resolve_excel_auto.basic.ValueEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/24
 * @modified By:
 * @project: resolve_excel_auto
 */
public interface ReadExcelService {
    /**
     * @param file
     * @return
     */
    List<ColumnEntity> getExcelColumnList(MultipartFile file);

    /**
     * @param file
     * @param columnEntityList
     * @return
     */
    Map<Integer, List<ValueEntity>> getExcelRowDataMap(MultipartFile file, List<ColumnEntity> columnEntityList);

}
