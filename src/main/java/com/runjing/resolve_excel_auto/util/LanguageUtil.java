package com.runjing.resolve_excel_auto.util;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : forestSpringH
 * @description:
 * @date : Created in 2023/8/18
 * @modified By:
 * @project: resolve_excel_auto
 */
@Slf4j
public class LanguageUtil {

    /**
     * 定义输出格式
     */
    public static HanyuPinyinOutputFormat hpFormat = new HanyuPinyinOutputFormat();
    /**
     * 匹配所有东亚区的语言
     */
    public static String CHINESE_CHAR_REG_SOUTHEAST_ASIA ="^[\u2E80-\u9FFF]+$";
    /**
     * 匹配简体和繁体
     */
    public static String CHINESE_CHAR_REG_SIMPLIFIED_OR_TRADITIONAL ="^[\u4E00-\u9FFF]+$";
    /**
     * 匹配简体
     */
    public static String CHINESE_CHAR_REG_SIMPLIFIED ="[\u4E00-\u9FA5]+$";

    static{
        // 大写格式输出
        hpFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        // 不需要语调输出
        hpFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    /***
     * 将汉字转成拼音(取首字母或全拼)
     * @param singleChar 中文字符
     * @param full 是否全拼
     * @return 转换后拼音
     */
    public static String convertChineseChar2Pinyin(String singleChar, boolean full,String regExp ) {

        StringBuffer sb = new StringBuffer();
        if (singleChar == null || "".equals(singleChar.trim())) {
            return "";
        }
        String pinyin = "";
        for (int i = 0; i < singleChar.length(); i++) {
            char unit = singleChar.charAt(i);
            //是汉字，则转拼音
            if (match(String.valueOf(unit), regExp))
            {
                pinyin = convertSingleChineseChar2Pinyin(unit);
                if (full) {
                    sb.append(pinyin);
                } else {
                    sb.append(pinyin.charAt(0));
                }
            } else {
                sb.append(unit);
            }
        }
        return sb.toString();
    }

    /***
     * 将单个汉字转成拼音
     * @param singleChar 中文汉字
     * @return 拼音
     */
    private static String convertSingleChineseChar2Pinyin(char singleChar) {
        String[] res;
        StringBuffer sb = new StringBuffer();
        try {
            res = PinyinHelper.toHanyuPinyinStringArray(singleChar, hpFormat);
            //对于多音字，只用第一个拼音
            sb.append(res[0]);
        } catch (Exception e) {
            log.error("单个汉字转换成字符失败：{}",e.getMessage());
            return "";
        }
        return sb.toString();
    }

    /***
     * @param str 源字符串
     * @param regex 正则表达式
     * @return 是否匹配
     */
    public static boolean match(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    /**
     * 汉字字符串的的首拼拼成字符串
     * @param chineseLan 中文字符串
     * @return 拼音字符串
     */
    public static String convertChineseLan2PinYinAbbreviation(String chineseLan,String regExp) {
        String ret = "";
        // 将汉字转换为字符数组
        char[] charChineseLan = chineseLan.toCharArray();
        try {
            for (int i = 0; i < charChineseLan.length; i++) {
                if(String.valueOf(charChineseLan[i]).matches(regExp)) {
                    // 如果字符是中文,则将中文转为汉语拼音（获取全拼则去掉红色的代码即可）
                    ret += PinyinHelper.toHanyuPinyinStringArray(charChineseLan[i], hpFormat)[0].substring(0, 1);
                } else {
                    // 如果字符不是中文,则不转换
                    ret += charChineseLan[i];
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            log.error("获取汉字的的首拼失败：{}",e.getMessage());
        }
        return ret;
    }


    /**
     * 判断字符串中是否包含中文汉字
     *
     * @param content 字符串内容
     * @return true至少包含1个
     */
    public static boolean hasChinese(CharSequence content) {
        if (null == content) {
            return false;
        }
        String regex = "[\u2E80-\u2EFF\u2F00-\u2FDF\u31C0-\u31EF\u3400-\u4DBF\u4E00-\u9FFF\uF900-\uFAFF\uD840\uDC00-\uD869\uDEDF\uD869\uDF00-\uD86D\uDF3F\uD86D\uDF40-\uD86E\uDC1F\uD86E\uDC20-\uD873\uDEAF\uD87E\uDC00-\uD87E\uDE1F]+";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(content).find();
    }

    /**
     * 判断字符串是否为中文汉字
     *
     * @param content 字符串内容
     * @return true都是汉字
     */
    public static boolean isChinese(CharSequence content) {
        if (null == content) {
            return false;
        }
        String regex = "[\u2E80-\u2EFF\u2F00-\u2FDF\u31C0-\u31EF\u3400-\u4DBF\u4E00-\u9FFF\uF900-\uFAFF\uD840\uDC00-\uD869\uDEDF\uD869\uDF00-\uD86D\uDF3F\uD86D\uDF40-\uD86E\uDC1F\uD86E\uDC20-\uD873\uDEAF\uD87E\uDC00-\uD87E\uDE1F]+";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(content).matches();
    }


}
