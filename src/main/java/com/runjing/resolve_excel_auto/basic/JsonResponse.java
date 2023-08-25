package com.runjing.resolve_excel_auto.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/25
 * @modified By:
 * @project: resolve_excel_auto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResponse {
    private int code = -1;
    private String message;
    private String json;

    public static JsonResponse success(String json){
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setCode(0);
        jsonResponse.setJson(json);
        jsonResponse.setMessage("访问成功");
        return jsonResponse;
    }

    public static JsonResponse fail(String massage){
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setMessage(massage);
        return jsonResponse;
    }
}
