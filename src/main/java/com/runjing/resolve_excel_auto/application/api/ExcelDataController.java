package com.runjing.resolve_excel_auto.application.api;

import com.runjing.resolve_excel_auto.basic.JsonResponse;
import com.runjing.resolve_excel_auto.mysql.service.SqlDataProvider;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/22
 * @modified By:
 * @project: resolve_excel_auto
 */
@RestController
@RequestMapping("/data")
@CrossOrigin
@Slf4j
public class ExcelDataController {

    @Resource
    private SqlDataProvider sqlDataProvider;

    @GetMapping("/handle/sql")
    @ResponseBody
    public JsonResponse handleData(@RequestParam String sql) {
        String executed = sqlDataProvider.executeQuerySql(sql);
        return Objects.equals("", executed)
                ? JsonResponse.fail("sql执行错误")
                : JsonResponse.success(executed);
    }
}
