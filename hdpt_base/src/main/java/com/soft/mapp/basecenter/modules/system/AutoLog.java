package com.soft.mapp.basecenter.modules.system;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoLog {
    String value() default "";
    //日志类型 1、后台日志，2.前台日志,3 异常日志
    int logType() default 0;
    //日志操作类型 1、添加，2、查询、3、修改，4，删除
    int operateType() default 0;
    //参数类型0.实体类参数constom_tag标注;1、指定logParam参数进行翻译。 2.为多参数logConst翻译
    int paramType() default 0;
}