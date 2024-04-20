package com.app.device.utils;

import com.app.device.domain.system.CustomTag;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class Parser {
    private static final Logger LOG = LoggerFactory.getLogger(Parser.class);
    public static String getDesc(Field field) {
        String result = null;
        try {
            ReflectionUtils.makeAccessible(field);
            Annotation[] annotation = field.getAnnotations();
            for (Annotation tag : annotation) {
                if (tag instanceof CustomTag) {
                    result = ((CustomTag)tag).desc();
                    break;
                }
                if(tag instanceof ApiModelProperty){
                    result = ((ApiModelProperty)tag).value();
                    break;
                }

            }
        } catch (SecurityException e) {
            LOG.error(e.getMessage());
        }
        return result;
    }

    public static String getDesc(Object obj, String propertyName) {
        String result = null;
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                ReflectionUtils.makeAccessible(field);
                if (field.getName().equals(propertyName)) {
                    String desc = getDesc(field);
                    if (desc != null && !desc.isEmpty()) {
                        result = desc;
                        break;
                    }
                }
            }
        } catch (SecurityException e) {
            LOG.error(e.getMessage());
        }
        return result;
    }

    public static Map<String, String> getAllDesc(Object obj) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            return getResult(fields);
        } catch (SecurityException e) {
            LOG.error(e.getMessage());
            return null;
        }
    }

    public static Map<String, String> getAllDescByArgs(Object[] args) {
        try {
            Map<String, String>  maps=new HashMap<>();
            for (int i = 0; i < args.length; i++) {
                Field[] fields = args[i].getClass().getDeclaredFields();
                Map<String, String> map=getResult(fields);
                maps.putAll(map);
            }
            return maps;
        } catch (SecurityException e) {
            LOG.error(e.getMessage());
            return null;
        }
    }

    private static Map<String, String> getResult(Field[] fields) {
        Map<String, String> result = new HashMap<String, String>();
        for (Field field : fields) {
            ReflectionUtils.makeAccessible(field);
            if (!field.getName().equals("id")) {
                String desc = getDesc(field);
                if (desc != null && !desc.isEmpty())
                    result.put(field.getName(), getDesc(field));
            }
        }
        return result;
    }
}
