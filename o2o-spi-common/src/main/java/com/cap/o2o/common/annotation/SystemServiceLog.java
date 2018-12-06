package com.cap.o2o.common.annotation;

import java.lang.annotation.*;

/**
 * Created by ron on 2018/11/29.
 * 自定义注解 拦截service
 */

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemServiceLog {
    String description()  default "";
}
