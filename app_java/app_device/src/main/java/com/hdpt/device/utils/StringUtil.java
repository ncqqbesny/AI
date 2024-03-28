package com.hdpt.device.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.lang.Nullable;

import java.lang.reflect.Array;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtil {
    public static final String EMPTY = "";
    /**
     * 判断是否为数字
     */
    private static Pattern numericPattern = Pattern.compile("^[0-9\\-]+$");

    /**
     * 获得字符串中数字
     * @param str
     * @return
     */
    public static   String getStringNums(String str){
        //String str = "love234csdn3423java";
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String result = m.replaceAll("").trim();
        return  result;
    }

    /**
     * 判断字符串是否为json
     * @param str
     * @return
     */
    public static boolean isJSON(String str) {
        boolean result = false;
        try {
            Object obj= JSON.parse(str);
            result = true;
        } catch (Exception e) {
            result=false;
        }
        return result;
    }

    /**
     * 反转字符串
     * @param str
     * @return
     */
    public static char[] stringReversalRecursion(String str) {
        if (str == null || str.length() <= 1) {
            return null;
        }
        char[] chars = new char[str.length()];
        for (int i = str.length() - 1; i >= 0; i--) {
            chars[str.length() - 1 - i] = str.charAt(i);
        }
        return chars;
    }

    /**
     * 是否为日期格式
     *
     * @param value
     * @param format
     * @return
     */
    public static boolean isDate(String value, String format) {
        SimpleDateFormat sdf = null;
        ParsePosition pos = new ParsePosition(0);
        if ((value == null) || (isEmpty(format))) {
            return false;
        }
        try {
            sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            Date date = sdf.parse(value, pos);
            if (date == null) {
                return false;
            }
            return pos.getIndex() <= sdf.format(date).length();
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean isNotEmptyString(String str) {

        return (str != null) && (str.length() > 0);
    }

    public static boolean isEmptyString(String str) {

        return (str == null) || (str.length() < 1);
    }

    /**
     * 判断对象是否为空，如集合、数组、所属对象等
     *
     * @param obj
     * @return
     */

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        } else if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        return false;
    }

    /**
     * 判断对象是否为空，如集合、数组、所属对象等
     *
     * @param obj
     * @return
     */

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 是否包含特定字符，忽略大小写，如果给定两个参数都为<code>null</code>，返回true
     *
     * @param str     被检测字符串
     * @param testStr 被测试是否包含的字符串
     * @return 是否包含
     */
    public static boolean containsIgnoreCase(CharSequence str, CharSequence testStr) {
        if (null == str) {
            // 如果被监测字符串和
            return null == testStr;
        }
        return str.toString().toLowerCase().contains(testStr.toString().toLowerCase());
    }

    /**
     * 去除页面的非法字符检查
     *
     * @param str
     * @return
     */

    public static String replaceStr(String str) {
        if (str != null && str.length() > 0) {
            str = str.replaceAll("~", "");
            str = str.replaceAll(" ", "");
            str = str.replaceAll("　", "");
            str = str.replaceAll(" ", "");
            str = str.replaceAll("`", "");
            str = str.replaceAll("!", "");
            str = str.replaceAll("@", "");
            str = str.replaceAll("#", "");
            str = str.replaceAll("\\$", "");
            str = str.replaceAll("%", "");
            str = str.replaceAll("\\^", "");
            str = str.replaceAll("&", "");
            str = str.replaceAll("\\*", "");
            str = str.replaceAll("\\(", "");
            str = str.replaceAll("\\)", "");
            str = str.replaceAll("-", "");
            str = str.replaceAll("_", "");
            str = str.replaceAll("=", "");
            str = str.replaceAll("\\+", "");
            str = str.replaceAll("\\{", "");
            str = str.replaceAll("\\[", "");
            str = str.replaceAll("\\}", "");
            str = str.replaceAll("\\]", "");
            str = str.replaceAll("\\|", "");
            str = str.replaceAll("\\\\", "");
            str = str.replaceAll(";", "");
            str = str.replaceAll(":", "");
            str = str.replaceAll("'", "");
            str = str.replaceAll("\\\"", "");
            str = str.replaceAll("<", "");
            str = str.replaceAll(">", "");
            str = str.replaceAll("\\.", "");
            str = str.replaceAll("\\?", "");
            str = str.replaceAll("/", "");
            str = str.replaceAll("～", "");
            str = str.replaceAll("`", "");
            str = str.replaceAll("！", "");
            str = str.replaceAll("＠", "");
            str = str.replaceAll("＃", "");
            str = str.replaceAll("＄", "");
            str = str.replaceAll("％", "");
            str = str.replaceAll("︿", "");
            str = str.replaceAll("＆", "");
            str = str.replaceAll("×", "");
            str = str.replaceAll("（", "");
            str = str.replaceAll("）", "");
            str = str.replaceAll("－", "");
            str = str.replaceAll("＿", "");
            str = str.replaceAll("＋", "");
            str = str.replaceAll("＝", "");
            str = str.replaceAll("｛", "");
            str = str.replaceAll("［", "");
            str = str.replaceAll("｝", "");
            str = str.replaceAll("］", "");
            str = str.replaceAll("｜", "");
            str = str.replaceAll("＼", "");
            str = str.replaceAll("：", "");
            str = str.replaceAll("；", "");
            str = str.replaceAll("＂", "");
            str = str.replaceAll("＇", "");
            str = str.replaceAll("＜", "");
            str = str.replaceAll("，", "");
            str = str.replaceAll("＞", "");
            str = str.replaceAll("．", "");
            str = str.replaceAll("？", "");
            str = str.replaceAll("／", "");
            str = str.replaceAll("·", "");
            str = str.replaceAll("￥", "");
            str = str.replaceAll("……", "");
            str = str.replaceAll("（", "");
            str = str.replaceAll("）", "");
            str = str.replaceAll("——", "");
            str = str.replaceAll("-", "");
            str = str.replaceAll("【", "");
            str = str.replaceAll("】", "");
            str = str.replaceAll("、", "");
            str = str.replaceAll("”", "");
            str = str.replaceAll("’", "");
            str = str.replaceAll("《", "");
            str = str.replaceAll("》", "");
            str = str.replaceAll("“", "");
            str = str.replaceAll("。", "");
        }
        return str;
    }

    /**
     * 移除html标签
     *
     * @param htmlstr
     * @return
     */
    public static String removeHtmlTag(String htmlstr) {
        Pattern pat = Pattern.compile("\\s*<.*?>\\s*", Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        Matcher m = pat.matcher(htmlstr);
        String rs = m.replaceAll("");
        rs = rs.replaceAll("&nbsp", " ");
        rs = rs.replaceAll("&lt;", "<");
        rs = rs.replaceAll("&gt;", ">");
        return rs;
    }


    /**
     * 比较两个字符串是否相等。
     *
     * @param str1       要比较的字符串1
     * @param str2       要比较的字符串2
     * @param ignoreCase 是否忽略大小写
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equals(CharSequence str1, CharSequence str2, boolean ignoreCase) {
        if (null == str1) {
            // 只有两个都为null才判断相等
            return str2 == null;
        }
        if (null == str2) {
            // 字符串2空，字符串1非空，直接false
            return false;
        }

        if (ignoreCase) {
            return str1.toString().equalsIgnoreCase(str2.toString());
        } else {
            return str1.equals(str2);
        }
    }

    /**
     * 判断一个字符串是否为数字
     *
     * @param src
     * @return
     */
    public static boolean isNumeric(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = numericPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /**
     * 进行tostring操作，如果传入的是null，返回空字符串。
     * StringUtils.toString(null)         = ""
     * StringUtils.toString(Boolean.TRUE) = "true"
     *
     * @param obj 源
     * @return String
     */
    public static String toString(Object obj) {
        return obj == null ? EMPTY : obj.toString();
    }

    /**
     * 进行tostring操作，如果传入的是null，返回指定的默认值。
     * StringUtils.toString(null, null)           = null
     * StringUtils.toString(null, "null")         = "null"
     * StringUtils.toString(null, "")           = ""
     * StringUtils.toString(Boolean.TRUE, "null") = "true"
     *
     * @param obj     源
     * @param nullStr 如果obj为null时返回这个指定值
     * @return String
     */
    public static String toString(Object obj, String nullStr) {
        return obj == null ? nullStr : obj.toString();
    }

    /**
     * 把字符串转换为html代码
     *
     * @param str
     * @return
     */
    @Nullable
    public static String replaceHtml(String str) {
        try {
            str = str.trim();
            str = str.replaceAll("&", "&amp;");
            str = str.replaceAll("<", "&lt;");
            str = str.replaceAll(">", "&gt;");
            str = str.replaceAll(" ", "&nbsp;");
            str = str.replace("'", "&#39;");
            str = str.replaceAll("\"", "&quot;");
            str = str.replace("\r\n", "<br />");
            str = str.replace("\n", "<br />");
            str = str.replace("\r", "<br />");
            return str;
        } catch (NullPointerException e) {
            return null;
        }
    }
}

