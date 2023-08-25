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
     * 读取EXCEL的列属性列表
     *
     * @param file 文件
     * @return 列属性实体列表（只含有列的属性）
     */
    List<ColumnEntity> getExcelColumnList(MultipartFile file);

    /**
     * 读取每一行的行实体列表，一个LIST为一行
     *
     * @param file             文件
     * @param columnEntityList 列实体列表
     * @return 全部的值MAP<行号 ， 行的属性值LIST>
     */
    Map<Integer, List<ValueEntity>> getExcelRowDataMap(MultipartFile file, List<ColumnEntity> columnEntityList);

}
