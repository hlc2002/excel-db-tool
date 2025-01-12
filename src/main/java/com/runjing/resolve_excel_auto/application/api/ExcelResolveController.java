package com.runjing.resolve_excel_auto.application.api;

import com.runjing.resolve_excel_auto.application.service.instrcution.ExcelEntityService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping("/createAndInsert")
    @ResponseBody
    public void uploadExcel(@RequestParam("fileName") MultipartFile file) {
        excelEntityService.createTable(file);
        excelEntityService.insertEntity(file);
    }

    @RequestMapping("/insert")
    @ResponseBody
    public void insertExcel(@RequestParam("fileName") MultipartFile file) {
        excelEntityService.insertEntity(file);
    }

    @RequestMapping("/drop")
    @ResponseBody
    public void delData(@RequestParam("fileName")String fileName){
        excelEntityService.dropTable(fileName);
    }


}