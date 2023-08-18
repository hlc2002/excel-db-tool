package com.runjing.resolve_excel_auto.application.api;

import com.runjing.resolve_excel_auto.application.service.instrcution.ExcelEntityService;
import com.runjing.resolve_excel_auto.basic.ColumnEntity;
import com.runjing.resolve_excel_auto.basic.ValueEntity;
import com.runjing.resolve_excel_auto.excel.ReadExcel;
import com.runjing.resolve_excel_auto.mysql.SqlDataService;
import com.runjing.resolve_excel_auto.mysql.SqlSplicer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.ref.PhantomReference;
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
@RestController
@RequestMapping("/excel")
@CrossOrigin
@Slf4j
public class ExcelResolveController {

    @Resource
    private ExcelEntityService excelEntityService;

    @RequestMapping("/upload")
    @ResponseBody
    public void uploadExcel(@RequestParam("fileName") MultipartFile file) {
        excelEntityService.createTable(file);
        excelEntityService.insertEntity(file);
    }

    @RequestMapping("/upload/insert")
    @ResponseBody
    public void insertExcel(@RequestParam("fileName") MultipartFile file) {
        excelEntityService.insertEntity(file);
    }
}