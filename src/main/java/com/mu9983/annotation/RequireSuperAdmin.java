package com.mu9983.annotation;

import java.lang.annotation.*;

/**
 * 非管理员接口隐藏注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireSuperAdmin {
}
