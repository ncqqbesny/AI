package com.hdpt.device.utils;
import com.hdpt.device.handler.IMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * 多语言查询value工具类
 *
 * @author yzg
 * @date 2017-11-6
 *
 */
public class I18nUtil {
    private static MessageSource messageSource;
    private final static Logger log = LoggerFactory.getLogger(I18nUtil.class);
    static {
        //messageSource = AppContext.getBean("messageSource");
    }
    /**
     * 根据CODE查询，默认无通配参数，Local跟随当前cookie
     * @Author: yzg
     * @Description:
     * @Date: 2017-11-6
     * @param: code
     */
    public static String getMessage(String code){
        return getMessage(code,null,getLocal());
    }

    public static String getMessage(String code,Locale locale){
        return getMessage(code,null,locale);
    }

    /**
     * 根据CODE查询，自定义默认值，默认无通配参数，Local跟随当前cookie
     * @Author: yzg
     * @Description:
     * @Date: 2017-11-6
     * @param: code
     */
    public static String getMessage(String code,String defaultMessage){
        return getMessage(code,null,defaultMessage,getLocal());
    }
    //注意修改
    public static String getMessage(IMessage code, Object[] defaultMessage){
        return getMessage(code.getCategory(),null,defaultMessage.toString(),getLocal());
    }
    /**
     * 根据CODE和args查询，Local跟随当前cookie
     * @Author: yzg
     * @Description:
     * @Date: 2017-11-6
     * @param: code
     * @param: args 通配符的参数
     */
    public static String getMessage(String code,Object[] args){
        return getMessage(code,args,getLocal());
    }

    public static String getMessage(String code,Object[] args,String defaultMessage,Locale locale){
        return messageSource.getMessage(code,args,defaultMessage,locale);
    }

    public static String getMessage(String code, Object[] args, Locale locale){
        try{
            return messageSource.getMessage(code,args,locale);
        }catch (Exception e){
            log.error("Query message value by key[{}] error. The reason is:"+e.getMessage(),code);
            return null;
        }
    }

    public static String getMessage(MessageSourceResolvable resolvable, Locale locale){
        try{
            return messageSource.getMessage(resolvable,locale);
        }catch (Exception e){
            log.error("Query message value error. The reason is:"+e.getMessage());
            return null;
        }
    }

    //解析用户区域
    public static Locale getLocal() {
        return LocaleContextHolder.getLocale();
    }
}