package com.hdpt.device.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RequestTools

{
    /**
     * 2      * 获取URL中的参数名和参数值的Map集合
     * 3      * @param url
     * 4      * @return
     * 5
     */
    public static Map<String, String>  getUrlPramNameAndValue(String url) {
        String regEx = "(\\?|&+)(.+?)=([^&]*)";//匹配参数名和参数值的正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(url);
        // LinkedHashMap是有序的Map集合，遍历时会按照加入的顺序遍历输出
        Map<String, String> paramMap = new LinkedHashMap<String, String>();
        while (m.find()) {
            String paramName = m.group(2);//获取参数名
            String paramVal = m.group(3);//获取参数值
            paramMap.put(paramName, paramVal);
        }
        return paramMap;
    }
}

