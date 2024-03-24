package com.hdpt.device.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Mybatis Plus Model 插入操作和更新操作默认值
 */
@Component
public class MyDeviceObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        setValue("createTime", new Date(), metaObject);
        setValue("lastTime", new Date(), metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setValue("lastTime", new Date(), metaObject);
    }
    private void setValue(String fieldName, Object value, MetaObject metaObject) {
        Object field = getFieldValByName(fieldName, metaObject);
        if(field == null && value != null) {
            setFieldValByName(fieldName, value, metaObject);
        }
    }

}
