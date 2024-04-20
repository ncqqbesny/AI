package com.app.device.domain.system;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CustomTag {
    String desc();
    int descType() default 0;
}
