package com.runjing.resolve_excel_auto.application.service.instrcution;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/17
 * @modified By:
 * @project: resolve_excel_auto
 */
public interface ExcelEntityService {
    /**
     * 创建数据表
     *
     * @param file 文件
     */
    void createTable(MultipartFile file);

    /**
     * 插入数据实体
     *
     * @param file 文件
     */
    void insertEntity(MultipartFile file);

    /**
     * 删除数据表
     * @param fileName 文件名
     */
    void dropTable(String fileName);
}