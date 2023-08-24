package com.runjing.resolve_excel_auto.util;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/24
 * @modified By:
 * @project: resolve_excel_auto
 */
public class SqlSpliceStringUtil {

    /**
     * 反引号处理
     *
     * @param fieldName 字段名称
     * @return 被反引号包裹得字段名称
     */
    public static String quotesHandle(String fieldName) {
        return "`" + fieldName + "`";
    }

    /**
     * 将汉字串转成拼音串
     *
     * @param columnChineseName 汉字字段名
     * @return 字段拼音
     */
    public static String transferPinYin(String columnChineseName) {
        /*转换中文为简体拼音*/
        return LanguageUtil.convertChineseLan2PinYinAbbreviation(columnChineseName, LanguageUtil.CHINESE_CHAR_REG_SIMPLIFIED);
    }
}
