package com.runjing.resolve_excel_auto.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/25
 * @modified By:
 * @project: resolve_excel_auto
 */
public class JsonUtil {

    private final static String arg = "\"";

    /**
     * Map转成JSON字符串
     *
     * @param map
     * @return JSON
     */
    public static String mapToJsonString(Map<String, Object> map) {
        return CollectionUtils.isEmpty(map) ? "" : JSONObject.toJSONString(map).replace(arg,"'");
    }

}
